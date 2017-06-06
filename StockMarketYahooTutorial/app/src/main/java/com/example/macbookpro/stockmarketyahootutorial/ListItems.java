package com.example.macbookpro.stockmarketyahootutorial;

/**
 * Created by MacBookPro on 6/6/16.
 */
public class ListItems {

    // This class creates the methods that allows us to create a List Item. This is created before the ListItemsAdapter.


    // Every stock should have these 3 pieces of information
    private String title;
    private String message;
    private Category category;



    public enum Category {
        PERSONAL, TECHNICAL, FINANCE, MEDICAL, SOCIAL
    }


    public ListItems(String title, String message, Category category) {
        this.title = title;
        this.message = message;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Category getCategory() {
        return category;
    }

    public int getAssociatedDrawable()
    {
        return categoryToDrawable(category);
    }



    // Personal (P icon), Technical (T icon), Finance (F icon), Medical (M icon).
    public static int categoryToDrawable(Category noteCategory)
    {
        switch (noteCategory)
        {
            case PERSONAL:
                return R.drawable.p;
            case TECHNICAL:
                return R.drawable.t;
            case FINANCE:
                return R.drawable.f;
            case MEDICAL:
                return R.drawable.m;
            case SOCIAL:
                return R.drawable.s;

        }
        // default value
        return R.drawable.p;
    }




}
