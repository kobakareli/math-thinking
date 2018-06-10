<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Test extends Model
{
    // fields

    protected $fillable = [
        'title_en', 'title_ge'
    ];

    // relations

    public function categories()
    {
        return $this->belongsToMany('App\Category', 'tests_categories',
                'test_id', 'category_id');
    }

    public function tasks()
    {
        return $this->belongsToMany('App\Task', 'tasks_tests',
                'test_id', 'task_id');
    }

    public function users()
    {
        return $this->belongsToMany('App\Test', 'user_tests',
                'test_id', 'user_id')->withPivot('status', 'score')->withTimestamps();;
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

    /* manipulate tasks */
    public function addTask($taskId)
    {
        $this->tasks()->attach($taskId);
    }
    public function removeTask($taskId)
    {
        $this->tasks()->detach($taskId);
    }
    public function removeAllTasks()
    {
        $this->tasks()->detach();
    }
}
