<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Category extends Model
{

    // fields

    protected $fillable = [
        'title_en', 'title_ge'
    ];

    // relations

    public function articles()
    {
        return $this->belongsToMany('App\Article', 'articles_categories',
                'category_id', 'article_id');
    }
}
