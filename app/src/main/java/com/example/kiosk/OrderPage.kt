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
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
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

    // ?????? ?????? ?????? list
    // ??? ????????? ????????? add, ?????????????????? ???????????? removeAt
    var entireOrderList = mutableListOf<Order>()
    // ?????? ??? ???????????? string ???????????? list??? ?????? (ex. "[??????] ????????? ??????", ...)
    var orderList = mutableListOf<String>()
    // ????????? ????????? ??????
    var count = 0
    // ?????? + ?????? + ????????? + ?????? ?????? (???????????? ????????? ????????? ???????????? ??????)
    // ????????? ??? ??? ???????????? ?????? ?????? ??????
    var totalCount = 0
    // ????????? 1, ???????????? 0
    var setOrNot = 0
    var totalBill = 1000

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

    var pattyOutOfAmountList = ArrayList<String>()
    var sideOutOfAmountList = ArrayList<String>()
    var drinkOutOfAmountList = ArrayList<String>()
    var cheeseOutOfAmountList = ArrayList<String>()
    var vegetableOutOfAmountList = ArrayList<String>()

    lateinit var tempButton: Button


    //Order??? ????????? product???
    var mainPatty = Product("?????????",1)
    var mainCheese = Product("????????????",1)
    var mainSauce = Product("???????????????",1)
    var mainVegetable = ArrayList<Product>()
    var addMenuList = ArrayList<Product>()

    var numberOfSides = arrayListOf<Int>(0,0,0)
    var numberOfDrinks = arrayListOf<Int>(0,0)

    var sideNames = arrayListOf<String>("????????????","????????????","????????????")
    var drinkNames = arrayListOf<String>("?????????","????????????")

    //???????????? ????????? ?????? ????????????, ????????????, ?????????, ?????? ?????? ??????
    var burgerMenus = arrayListOf<Product>()
    var addMenus = arrayListOf<Product>()
    var sideMenus = arrayListOf<Product>()
    var drinkMenus = arrayListOf<Product>()

    var productList = arrayListOf<Product>() //?????? ?????? + ?????????
    var order = Order()
    var total = 0 //?????????


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        database.addValueEventListener(postListener)
        var num = 0

        //???????????? ????????? -> ???????????????????????? ????????? ?????? matching?????? ?????? ?????????
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


        //?????? ????????? ????????? ?????? ??? ?????? ???????????? ??????
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
        // OrderListBtn.setBackgroundColor(Color.parseColor("#00ff0000"));
        OrderListBtn.setTextColor(resources.getColor(R.color.brown_900))

        //add Button activity ????????? ??????
        requestLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){

                val resultData = it.data?.getStringExtra("result")
                var btn = Button(this)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(changeDP(0), changeDP(0), changeDP(0), changeDP(0))

                btn.layoutParams = layoutParams
                binding.addButtonView.addView(btn)
                addmenucount++
                if(addmenucount>=2){
                    binding.addBtn.visibility=INVISIBLE;
                }
                newbutton = btn
                newbutton.setBackgroundColor(Color.parseColor("#00ff0000"));
                newbutton.typeface = resources.getFont(R.font.rixinooariduriregular)
                newbutton.setTextColor(resources.getColor(matchColor(resultData)))
                newbutton.text = resultData
                newbutton.textSize = changeDP(11).toFloat()

                var product = Product(resultData!!,1)
                addMenuList.add(product)

                newbutton.setOnClickListener{
                    binding.addButtonView.removeView(btn)
                    addMenuList.remove(product)
                    if(addmenucount==2)
                        binding.addBtn.visibility=VISIBLE;
                    addmenucount--
                }

            }
        }

        //add button listener
        binding.addBtn.setOnClickListener {
            if(addmenucount <= 1) {
                val addOrderIntent = Intent(this, AddMenuDialog::class.java)
                requestLaunch.launch(addOrderIntent)
            }
            else{
                Toast.makeText(this, "????????? ????????? ????????? ??? ????????????!", Toast.LENGTH_SHORT).show()
            }

        }

//        //????????? ??????
//        binding.btnMainPatty.setOnClickListener{
//            val bundle = Bundle()
//            bundle.putStringArrayList("pattyAmount",pattyOutOfAmountList)
//
//            val pattyFragmentDialog = PattyFragmentDialog()
//            pattyFragmentDialog.arguments=bundle
//
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.commit()
//        }

        //main patty listener
        binding.btnMainPatty.setOnClickListener {
            val dialog = PattyDialog(this)
            dialog.onDialog()
            dialog.setOnClickListener(object : PattyDialog.OnDialogClickListener {

                override fun onClicked(name: String) {

                    Log.d("dialog",name)
                    pattyName = name
                    binding.btnMainPatty.text = pattyName
                    mainPatty.name = pattyName
                    mainPatty.price = getPrice("patty",pattyName)

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
                    mainSauce.name = sauceName
                    mainSauce.price = getPrice("sauce",sauceName)
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
                    mainCheese.name = cheeseName
                    mainCheese.price = getPrice("cheese",cheeseName)
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
                    var i = 0
                    for(veges in selectedVegetable){
                        mainVegetable.add(Product(veges,1,getPrice("vegetable",veges)))
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

                //side ?????? ?????? ??? ?????? ??????

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
                            "frenchfries"  -> {
                                sideimg =
                                    resources.getDrawable(R.drawable.frenchfries)
                                numberOfSides[0]++

                            }
                            "cheesefries" -> {
                                sideimg =
                                    resources.getDrawable(R.drawable.cheesefries)
                                numberOfSides[1]++

                            }
                            "chickenfries" -> {
                                sideimg =
                                    resources.getDrawable(R.drawable.chickenfries)
                                numberOfSides[2]++

                            }

                        }

                        binding.addSideButtonView.addView(btn)

                        sidecount++
                        newbutton = btn
                        newbutton.background = sideimg
                        newbutton.setOnClickListener{
                            binding.addSideButtonView.removeView(btn)
                            sidecount--
                            when (btn.id){
                                R.id.sideFrenchfriesIcon -> numberOfSides[0]--
                                R.id.sideCheesefriesIcon -> numberOfSides[1]--
                                R.id.sideChickenfriesIcon -> numberOfSides[2]--
                            }
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
                            "cider" -> {
                                drinkimg =
                                    resources.getDrawable(R.drawable.cider)
                                numberOfDrinks[0]++

                            }
                            "coke" -> {
                                drinkimg =
                                    resources.getDrawable(R.drawable.coke)
                                numberOfDrinks[1]++

                            }
                        }

                        binding.addDrinkButtonView.addView(btn)
                        drinkcount++
                        newbutton = btn
                        newbutton.background = drinkimg
                        newbutton.setOnClickListener{
                            binding.addDrinkButtonView.removeView(btn)
                            drinkcount--
                            when (btn.id){
                                R.id.drinkCiderIcon -> numberOfDrinks[0]--
                                R.id.drinkCokeIcon -> numberOfDrinks[1]--
                            }
                        }
                    }
                })
            }
        }

        binding.moreOrder.setOnClickListener{
            OrderListBtn.setVisibility(View.VISIBLE)

            var total = 0

            //???????????? ???????????? ?????? -> productList


            burgerMenus.add(mainPatty)
            burgerMenus.add(mainCheese)
            burgerMenus.add(mainSauce)
            for (p in mainVegetable)
                burgerMenus.add(p)
            for (p in addMenuList){
                p.price = getPrice("add",p.name);
                addMenus.add(p)
            }


            //side ??????
            for (i in 0..2) {
                if (numberOfSides[i] != 0)
                    sideMenus.add(Product(sideNames[i], numberOfSides[i],getPrice("side",sideNames[i])))

                if (i<=1 && numberOfDrinks[i] != 0)
                    drinkMenus.add(Product(drinkNames[i], numberOfDrinks[i],getPrice("drink",drinkNames[i])))
            }

            //?????? ?????????, ????????? ??????
            for (p in burgerMenus) {
                productList.add(p)
                total += p.price * p.num
                totalCount = totalCount + p.num
            }
            for (p in addMenus) {
                productList.add(p)
                total += p.price * p.num
                totalCount = totalCount + p.num
            }
            for (p in sideMenus) {
                productList.add(p)
                total += p.price * p.num
                totalCount = totalCount + p.num
                if(p.num != 0)
                    setOrNot = 1
            }
            for (p in drinkMenus) {
                productList.add(p)
                total += p.price * p.num
                totalCount = totalCount + p.num
                if(p.num != 0)
                    setOrNot = 1
            }

//            for(a in productList)
//                Log.d("productList",a.name+a.num)

            Log.d("totalAmount",total.toString());
            //order??? ????????? ??????
            order.lists.addAll(productList)
            order.price = total

            entireOrderList.add(order)
            totalBill = totalBill + entireOrderList[count].price

            var tempString = "a"
            if(setOrNot == 1)
                tempString = "\n[??????] "
            else
                tempString = "\n[??????] "
            tempString = tempString + entireOrderList[count].lists[0].name + " ??????\n"
            if(setOrNot == 1)
                tempString = tempString + "\t(" + entireOrderList[count].lists[totalCount-2].name + ", " + entireOrderList[count].lists[totalCount-1].name + ")"
            orderList.add(count, tempString)
            count++

            totalCount = 0
            setOrNot = 0


            //???????????? ?????????
            productList.clear()
            for (i in 0..2){
                numberOfSides[i] = 0
                if(i<=1)
                    numberOfDrinks[i] = 0
            }
            mainVegetable.clear()
            burgerMenus.clear()
            sideMenus.clear()
            drinkMenus.clear()

            for(a in order.lists)
                Log.d("productList",a.name+a.num)

            //?????? order ???????????? ????????? ?????? ??????


            //order clear
            order.lists.clear()
            order.price = 0


            //view ?????????
            binding.addDrinkButtonView.removeAllViews()
            binding.addSideButtonView.removeAllViews()
            binding.addButtonView.removeAllViews()
            binding.btnMainPatty.text = "?????? ?????????"
            binding.btnMainCheese.text = "?????? ?????????"
            binding.btnMainSauce.text = "?????? ?????????"
            binding.btnMainVegetable.text = "?????? ?????????"
            addmenucount = 0; sidecount = 0; drinkcount= 0
            pattyName=" "; sauceName=" "; cheeseName = " "
            sideName=" ";drinkName = " ";vegetableName=" "
            binding.addBtn.visibility=VISIBLE;
        }

        binding.completeOrder.setOnClickListener {
            val paymentIntent = Intent(this, PaymentActivity::class.java)
            paymentIntent.putExtra("totalBill", totalBill);
            startActivity(paymentIntent)
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
        var d2 : Int = R.color.green_600
        when(value){
            "?????????" -> d2 = R.color.brown_600
            "??????" -> d2 = R.color.brown_600
            "???????????????"-> d2 = R.color.brown_600
            "????????????"-> d2 = R.color.brown_600
            "??????" -> d2 = R.color.brown_600
            "???????????????" -> d2 = R.color.red_500
            "?????? ????????????" -> d2 = R.color.red_500
            "????????? ????????????" -> d2 = R.color.red_500
            "????????????" -> d2 = R.color.red_500
            "??????????????????" -> d2 = R.color.red_500
            "??????????????????" -> d2 = R.color.yellow_800
            "????????????" -> d2 = R.color.yellow_800
        }
        return d2
    }


    fun orderListDialog(): AlertDialog.Builder{

        var checkedItems = arrayListOf<String>()
        val builder = AlertDialog.Builder(this)
        var orderArray = emptyArray<String>()
        var checkCount = 0
        var deleteIndex = arrayListOf<Int>()
        val seperator = ""

        for(i in 0..count-1) {
            orderArray = orderArray.plus("")
            orderArray[i] = orderList[i]
        }

        // ?????? ???????????? ????????????
        entireOrderList.add(count, order)
        orderList.add(count, "")


        builder.setTitle("????????? ????????? ???????????????.")
            .setMultiChoiceItems(orderArray, null, object : DialogInterface.OnMultiChoiceClickListener {
                override fun onClick(dialogInterface: DialogInterface, pos: Int, isChecked: Boolean) {
                    if(isChecked) {
                        checkedItems.add(orderArray[pos])
                        deleteIndex.add(pos)
                        checkCount++
                    } else if(checkedItems.contains(orderArray[pos])) {
                        checkedItems.remove(orderArray[pos])
                        deleteIndex.remove(pos)
                        checkCount--
                    }
                }
            })

            .setPositiveButton("????????????", object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                    if(checkCount != 0)
                    {
                        for(i in 0..checkCount-1) {
                            // entire????????? ????????? ???
                            /*
                            for(k in 0..count-1)
                            {
                                val tempString = entireOrderList[k].lists.joinToString(seperator)
                                // if(checkedItems[i] == tempString)
                                   // entireOrderList.removeAt(k)
                            }
                             */

                            orderList.remove(checkedItems[i])
                            count--
                        }
                        totalBill = totalBill - 2000 * checkCount
                        Toast.makeText(baseContext, "${totalBill}", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            .setNegativeButton("????????????", object: DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {

                }
            })

        builder.create()

        return builder
    }

    fun getPrice(type : String, name : String): Int {
        Log.d("menuname?",name)
        var lists = mutableListOf<Product>()
        when (type) {
            "patty" -> lists = patty_list
            "sauce" -> return 0
            "vegetable" -> return 0
            "cheese" -> lists = cheese_list
            "side" -> lists = side_list
            "drink" -> lists = drink_list
            "add" -> {
                when(name) {
                    "?????????" -> lists = patty_list
                    "??????" -> lists = patty_list
                    "???????????????" -> lists = patty_list
                    "????????????" -> lists = patty_list
                    "??????" -> lists = patty_list
                    "??????????????????" -> lists = cheese_list
                    "????????????" -> lists = cheese_list
                }
            }
        }
        for(p in lists){
            if(p.name == name){
                Log.d("matchingName",name+p.price)
                return p.price
            }
        }

        return 0
    }
}