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

    public function tests()
    {
        return $this->belongsToMany('App\Test', 'tests_categories',
                'category_id', 'test_id');
    }

    public function tasks()
    {
        return $this->belongsToMany('App\Task', 'tasks_categories',
                'category_id', 'task_id');
    }

    public function supercategories()
    {
        return $this->belongsToMany('App\SuperCategory', 'super_categories_categories',
                'category_id', 'super_category_id');
    }
}
