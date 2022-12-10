package com.example.kiosk

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kiosk.R
import com.example.kiosk.databinding.ActivityMainpageBinding
import com.example.kiosk.databinding.DialogCheeseBinding
import com.example.kiosk.databinding.DialogPattyBinding
import com.example.kiosk.databinding.DialogSauceBinding
import com.example.kiosk.databinding.DialogVegetableBinding
import com.example.kiosk.databinding.LayoutMenuitemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import java.text.DecimalFormat
import kotlin.math.roundToInt


public class OrderPage : AppCompatActivity() {

    lateinit var requestLaunch: ActivityResultLauncher<Intent>
    lateinit var newbutton : Button
    lateinit var database : DatabaseReference

    var cheese_list = mutableListOf<Product>()
    var drink_list = mutableListOf<Product>()
    var patty_list = mutableListOf<Product>()
    var side_list = mutableListOf<Product>()
    var veg_list = mutableListOf<Product>()

    val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val datas = snapshot.child("Product")
            for (data in datas.children) {
                var key = data.key.toString()

                var items = snapshot.child("Product").child(key)
                for (item in items.children) {
                    var product = item.getValue(Product::class.java)
                    if (key == "cheese") {
                        if (product != null) {
                            cheese_list.add(product)
                        }
                    } else if (key == "drink") {
                        if (product != null) {
                            drink_list.add(product)
                        }
                    } else if (key == "patty") {
                        if (product != null) {
                            patty_list.add(product)
                        }
                    } else if (key == "side") {
                        if (product != null) {
                            side_list.add(product)
                        }
                    } else if (key == "vegetable") {
                        if (product != null) {
                            veg_list.add(product)
                        }
                    }
                }
            }
            // Log.d("List", cheese_list.toString())
            // Log.d("List", drink_list.toString())
            // Log.d("List", patty_list.toString())
            // Log.d("List", side_list.toString())
            // Log.d("List", veg_list.toString())
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    var addmenucount: Int = 0
    var sidecount: Int = 0
    var drinkcount: Int = 0
    var pattyName: String = " "
    var sauceName: String = " "
    var cheeseName: String = " "
    var sideName: String = " "
    var drinkName: String = " "
    var vegetableName: String = " "
    var burgerPrice: Int = 0
    var sidePrice: Int = 0
    var drinkPrice: Int = 0

    var pattyOutOfAmountList = ArrayList<String>()
    var sideOutOfAmountList = ArrayList<String>()
    var drinkOutOfAmountList = ArrayList<String>()
    var cheeseOutOfAmountList = ArrayList<String>()
    var vegetableOutOfAmountList = ArrayList<String>()

    lateinit var tempButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        database.addValueEventListener(postListener)
        var num = 0

        //테스트용 리스트 -> 데이터베이스에서 저장된 이름 matching하는 함수 만들기
        var patty_list2 = mutableListOf<Product>()
        var p1 = Product("Bbq",10,10)
        var p2 = Product("Steak",0,10)
        var p3 = Product("Eggsalad",10,10)
        var p4 = Product("Shrimp",10,10)
        var p5 = Product("Chicken",10,10)


        patty_list2.add(p1)
        patty_list2.add(p2)
        patty_list2.add(p3)
        patty_list2.add(p4)
        patty_list2.add(p5)


        //재고 확인후 이미지 변경 및 클릭 불가하게 변경
        for (p in patty_list2) {
            if (p.num <= 3) {
                pattyOutOfAmountList.add(p.name)
            }
        }


//        for (p in patty_list2) {
//            if (p.num <= 3) {
//                var nameId = "@+id/patty"+p.name[0].uppercase() + p.name.substring(1)+"Icon"
//                Log.d("outofamount", nameId)
//                Log.d("outofamount",resources.getIdentifier(nameId,"id","com.example.kiosk").toString() )
//                tempButton = findViewById(resources.getIdentifier(nameId,"id","com.example.kiosk"))
//                var img = resources.getIdentifier(p.name+"_soldout","drawable","com.example.kiosk")
//                tempButton.background = resources.getDrawable(img)
//                tempButton.isEnabled = false
//                R.id.pattySteakIcon
//            }
        val OrderListBtn = findViewById<Button>(R.id.btn_orderList)

        OrderListBtn.setOnClickListener {
            val builder = orderListDialog()
            builder.show()
        }
        OrderListBtn.setVisibility(View.INVISIBLE)
        OrderListBtn.setBackgroundColor(Color.parseColor("#00ff0000"));
        OrderListBtn.typeface = resources.getFont(R.font.rixinooariduriregular)
        OrderListBtn.setTextColor(resources.getColor(R.color.brown_600))
        // OrderListBtn.textSize = changeDP(30).toFloat()

        requestLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val resultData = it.data?.getStringExtra("result")
//                Log.d("finishJob","yes")
                var btn = Button(this)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(changeDP(0), changeDP(0), changeDP(0), changeDP(0))

                btn.layoutParams = layoutParams
                binding.addButtonView.addView(btn)
                addmenucount++
                newbutton = btn
                newbutton.setOnClickListener{
                    binding.addButtonView.removeView(btn)
                    addmenucount--
                }
                newbutton.setBackgroundColor(Color.parseColor("#00ff0000"));
                newbutton.typeface = resources.getFont(R.font.rixinooariduriregular)
                newbutton.setTextColor(resources.getColor(matchColor(resultData)))
                newbutton.text = resultData
                newbutton.textSize = changeDP(11).toFloat()

            }
        }
        binding.addBtn.setOnClickListener {
            if(addmenucount <= 2) {
                val addOrderIntent = Intent(this, AddMenuDialog::class.java)
                requestLaunch.launch(addOrderIntent)
            }
            else{
                Toast.makeText(this, "더이상 메뉴를 추가할 수 없습니다!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnMainPatty.setOnClickListener{
            val bundle = Bundle()
            bundle.putStringArrayList("pattyAmount",pattyOutOfAmountList)

            val pattyFragmentDialog = PattyFragmentDialog()
            pattyFragmentDialog.arguments=bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.commit()
        }

        binding.btnMainPatty.setOnClickListener {
            val dialog = PattyDialog(this)
            dialog.onDialog()
            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {

                override fun onClicked(name: String) {

                    Log.d("dialog",name)
                    pattyName = name
                    binding.btnMainPatty.text = pattyName
//                    binding.btnMainPatty.background=drawable
                }
            })


        }

        binding.btnMainSauce.setOnClickListener {
            val dialog = SauceDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : SauceDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    sauceName = name
                    binding.btnMainSauce.text = sauceName
//                    binding.btnMainSauce.background=drawable
                }
            })
        }

        binding.btnMainCheese.setOnClickListener {
            val dialog = CheeseDialog(this)
            dialog.onDialog()

            dialog.setOnClickListener(object : CheeseDialog.OnDialogClickListener {
                override fun onClicked(name: String) {
                    cheeseName = name
                    binding.btnMainCheese.text = cheeseName
                }
            })
        }



        binding.btnMainVegetable.setOnClickListener {

            val dialog = VegetableDialog(this)
            dialog.onDialog()
            var selectedVegetable = arrayListOf<String>()
            selectedVegetable.clear()
            vegetableName = ""

            dialog.setOnClickListener(object : VegetableDialog.OnDialogClickListener {
                override fun onClicked(list: ArrayList<String>) {
                    selectedVegetable = list
                    for(veges in selectedVegetable){
                        vegetableName += veges+" "
                    }
                    Log.d("vegetableSelect", vegetableName)
                    binding.btnMainVegetable.text = vegetableName
                }
            })
        }

        binding.addSideBtn.setOnClickListener {
            if (sidecount <= 2) {
                val dialog = SideDialog(this)
                lateinit var sideimg: Drawable
                dialog.onDialog()

                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
                var btn = Button(newStyle)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btn.layoutParams = layoutParams
                layoutParams.width = changeDP(80)
                layoutParams.height = changeDP(80)
                layoutParams.setMargins(changeDP(10), changeDP(10), changeDP(10), changeDP(10))

                dialog.setOnClickListener(object : SideDialog.OnDialogClickListener {
                    override fun onClicked(name: String) {

                        sideName = name
                        Log.d("dialog", name)
                        when (name) {
                            "frenchfries" -> sideimg =
                                resources.getDrawable(R.drawable.frenchfries)
                            "cheesefries" -> sideimg =
                                resources.getDrawable(R.drawable.cheesefries)
                            "chickenfries" -> sideimg =
                                resources.getDrawable(R.drawable.chickenfries)
                        }

                        binding.addSideButtonView.addView(btn)
                        sidecount++
                        newbutton = btn
                        newbutton.background = sideimg
                        newbutton.setOnClickListener{
                            binding.addSideButtonView.removeView(btn)
                            sidecount--
                        }
                    }
                })
            }
        }
        binding.addDrinkBtn.setOnClickListener {
            if (drinkcount <= 2) {
                val dialog = DrinkDialog(this)
                lateinit var drinkimg: Drawable
                dialog.onDialog()

                var newStyle = ContextThemeWrapper(this, R.style.Button_Border)
                var btn = Button(newStyle)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                btn.layoutParams = layoutParams
                layoutParams.width = changeDP(80)
                layoutParams.height = changeDP(80)
                layoutParams.setMargins(changeDP(10), changeDP(10), changeDP(10), changeDP(10))
                dialog.setOnClickListener(object : DrinkDialog.OnDialogClickListener {
                    override fun onClicked(name: String) {

                        drinkName = name
                        Log.d("dialog", name)
                        when (name) {
                            "cider" -> drinkimg =
                                resources.getDrawable(R.drawable.cider)
                            "coke" -> drinkimg =
                                resources.getDrawable(R.drawable.coke)
                        }

                        binding.addDrinkButtonView.addView(btn)
                        drinkcount++
                        newbutton = btn
                        newbutton.background = drinkimg
                        newbutton.setOnClickListener{
                            binding.addDrinkButtonView.removeView(btn)
                            drinkcount--
                        }
                    }
                })
            }
        }

        binding.moreOrder.setOnClickListener{
            OrderListBtn.setVisibility(View.VISIBLE)

            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.text = "패티 고르기"
            binding.btnMainCheese.text = "치즈 고르기"
            binding.btnMainSauce.text = "소스 고르기"
            binding.btnMainVegetable.text = "야채 고르기"
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "

        }
        binding.completeOrder.setOnClickListener{
            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.text = "패티 고르기"
            binding.btnMainCheese.text = "치즈 고르기"
            binding.btnMainSauce.text = "소스 고르기"
            binding.btnMainVegetable.text = "야채 고르기"
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "
            burgerPrice = 0
            sidePrice = 0
            drinkPrice = 0
        }

        binding.completeOrder.setOnClickListener {
            val pay = Intent(this, PaymentActivity::class.java)
            startActivity(pay)
        }

        binding.adminButton.setOnClickListener {
            val adminPassIntent = Intent(this, AdminPassword::class.java)
            startActivity(adminPassIntent)
        }
    }



    private fun changeDP(value: Int): Int {

        var displayMetrics = resources.displayMetrics
        var dp = (value * displayMetrics.density).roundToInt()
        return dp
    }
    private fun matchColor(value: String?): Int{
        var d2 : Int = 0
        when(value){
            "불고기" -> d2 = R.color.brown_600
            "새우" -> d2 = R.color.brown_600
            "달걀샐러드"-> d2 = R.color.brown_600
            "스테이크"-> d2 = R.color.brown_600
            "치킨" -> d2 = R.color.brown_600
            "바비큐소스" -> d2 = R.color.red_500
            "매운 칠리소스" -> d2 = R.color.red_500
            "달콤한 칠리소스" -> d2 = R.color.red_500
            "마늘소스" -> d2 = R.color.red_500
            "타르타르소스" -> d2 = R.color.red_500
            "모짜렐라치즈" -> d2 = R.color.yellow_800
            "체다치즈" -> d2 = R.color.yellow_800
            "양상추" -> d2 = R.color.green_600
            "양파" -> d2 = R.color.green_600
            "피클" -> d2 = R.color.green_600
            "토마토" -> d2 = R.color.green_600
        }
        return d2
    }


    fun orderListDialog(): AlertDialog.Builder{

        // orderList에 목록 받아오기
        // 여기 있는 데이터를 다음 과정으로 넘겨주기
        var orderList = arrayOf("새우버거 세트 (콜라, 감자튀김)", "불고기버거 단품")
        var checkedItems = arrayListOf<String>()
        val builder = AlertDialog.Builder(this)

        builder.setTitle("취소할 메뉴를 선택하세요.")
            .setMultiChoiceItems(orderList, null, object : DialogInterface.OnMultiChoiceClickListener {
                override fun onClick(dialogInterface: DialogInterface, pos: Int, isChecked: Boolean) {
                    if(isChecked) {
                        checkedItems.add(orderList[pos])
                        // array에서 값 삭제하기
                    } else if(checkedItems.contains(orderList[pos])) {
                        checkedItems.remove(orderList[pos])
                    }
                }
            })

            .setPositiveButton("취소하기", object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    // Toast.makeText(baseContext, "${checkedItems}", Toast.LENGTH_SHORT).show()
                }
            })

        builder.create()

        return builder
    }



}