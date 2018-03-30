<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Article extends Model
{

    // fields

    protected $fillable = [
        'title_en', 'title_ge', 'text_en', 'text_ge'
    ];

    // relations

    public function categories()
    {
        return $this->belongsToMany('App\Category', 'articles_categories',
                'article_id', 'category_id');
    }


    /* manipulate categories */
    public function addCategory($categoryId)
    {
        $this->categories()->attach($categoryId);
    }
    public function removeCategory($categoryId)
    {
        $this->categories()->detach($categoryId);
    }
    public function removeAllCategories()
    {
        $this->categories()->detach();
    }
}
