package com.example.hotelapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelapp.R;
import com.example.hotelapp.adapter.CategoryAdapter;
import com.example.hotelapp.adapter.ProductAdapter;
import com.example.hotelapp.model.Category;
import com.example.hotelapp.model.Product;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView categoriesList;
    RecyclerView productList;

    ArrayList<Category> categoriesItem;
    ArrayList<Product> productItem;

    ImageCarousel imageCarousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriesList=findViewById(R.id.categoriesList);
        productList=findViewById(R.id.productList);
        imageCarousel=findViewById(R.id.carousel);

        imageCarousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list=new ArrayList<>();
        list.add(new CarouselItem(R.drawable.food_delivery1));
        list.add(new CarouselItem(R.drawable.food_delivey2));
        list.add(new CarouselItem(R.drawable.biryani_offer));

         imageCarousel.setData(list);



        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        categoriesList.setLayoutManager(layoutManager);

        // Categories List
        categoriesItem=new ArrayList<>();
        categoriesItem.add(new Category("Veg","#18ab4e",R.drawable.veg,1));
        categoriesItem.add(new Category("Non-Veg","#fb0504",R.drawable.nonveg,2));

        CategoryAdapter categoryAdapter=new CategoryAdapter(getApplicationContext(),categoriesItem);
        categoriesList.setAdapter(categoryAdapter);


        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        productList.setLayoutManager(layoutManager1);


        // Products List
        productItem=new ArrayList<Product>();
        productItem.add(new Product("Veg Thali","Ready Stock","Veg Thali is a round platter used to serve food in South Asia, Southeast Asia and the Caribbean. Thali is also used to refer to an Indian-style meal made up of a selection of various dishes which are served on a platter.",150,0,1,R.drawable.veg_thali,22));
        productItem.add(new Product("NonVeg Thali","Ready Stock","A thali is a combination of various dishes put together to make a whole meal. An average non-vegetarian thali usually contains salad, rice, chappati and gravy or dry chicken/mutton/fish dish to go with it.",200,0,2,R.drawable.nonveg_thali,22));
        productItem.add(new Product("Matar Paneer","Ready Stock","Matar paneer recipe is a popular Indian Curry dish made with green peas and Paneer (Indian cottage cheese) in a base of onions, tomatoes, cashews, spices and herbs. The recipe hails from the versatile Northern Indian cuisine that has many different (and delicious) variations.",350,0,1,R.drawable.paneer_matar,22));
        productItem.add(new Product("Rice","Ready Stock","Rice is the seed of a semi-aquatic grass (Oryza sativa) that is cultivated extensively in warm climates in many countries, including the United States, for its edible grain. It is a staple food throughout the world.",100,0,1,R.drawable.rice,22));
        productItem.add(new Product("Chicken Handi","Ready Stock","Chicken Handi translates to chicken in clay pot. Traditionally this curry is made in a terrocota pot which imparts its unique flavour to this dish. Chicken Handi is milder than other South Asian curries but still rich in flavour. The chicken is cooked in a creamy base of yogurt and cream.",450,0,2,R.drawable.chicken_handi,22));
        productItem.add(new Product("Palak Paneer","Ready Stock","Palak paneer is a classic curried dish from North Indian cuisine made with fresh spinach, onions, spices, paneer and herbs. 'Palak' is a Hindi word for 'Spinach' and 'Paneer' is 'Indian cottage cheese'. So Palak Paneer translates to paneer simmered in a smooth spicy and delicious spinach gravy or sauce.",320,0,1,R.drawable.palak_paneer,22));
        productItem.add(new Product("Aloo Matar","Ready Stock","Aloo Matar is a North Indian dish from the Indian subcontinent which is made from potatoes (Aloo) and peas (mattar) in a mildly spiced creamy tomato based gravy. It is a vegetarian dish.",300,0,1,R.drawable.aloo_matar,22));
        productItem.add(new Product("Mutton Handi","Ready Stock","Handi Mutton is one of the most loved mutton delicacies of the Northern region of India. As the name suggests, the dish is traditionally cooked in a Handi or an earthen pot for long hours. The Handi Mutton usually consists of bones of the lamb and slow booking brings out the best possible flavours",550,0,2,R.drawable.mutton_handi,22));
        productItem.add(new Product("Roti","Ready Stock","The roti is a traditional flatbread from the Indian subcontinent. It is normally eaten with cooked vegetables or curries; it can be used as a carrier for them. It is made most often from wheat flour, cooked on a flat or slightly concave iron griddle called a tawa.",30,0,1,R.drawable.roti,100));
        productItem.add(new Product("Fish Dish","Ready Stock","Fish dishes are distinct food dishes which use seafood (fish, shellfish or seaweed) as primary ingredients, and are ready to be served or eaten with any needed preparation or cooking completed.",500,0,2,R.drawable.fish_dish,22));
        productItem.add(new Product("Dal Tadka","Ready Stock","Dal tadka is cooked lentils which are tempered with oil or ghee fried spices & herbs. I have to admit that I simply love the dal tadka that is served at the restaurants even though we hardly eat out.",150,0,1,R.drawable.dal_thadka,22));
        productItem.add(new Product("Chicken Biryani","Ready Stock","Biryani is a celebratory rice and meat dish cherished in the Indian sub-continent. A traditional biryani consists of fluffy basmati rice layered over tender & succulent pieces of meat, accompanied with the mesmerizing aromas of spices, herbs & caramelized onions.",150,0,2,R.drawable.chicken_biryani,22));
        productItem.add(new Product("Anda kari","Ready Stock","Punjabi Egg Curry (Anda Curry) is a spicy, robust Indian curry where hard-boiled eggs are simmered in a spicy onion tomato gravy. This gluten-free and dairy-free curry comes together in under 30 minutes using basic ingredients.",250,0,2,R.drawable.aanda_kari,22));



        ProductAdapter productAdapter=new ProductAdapter(getApplicationContext(),productItem);
        productList.setAdapter(productAdapter);


    }
}