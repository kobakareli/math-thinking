<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Task extends Model
{
    // fields

    protected $fillable = [
        'title_en', 'title_ge', 'description_en', 'description_ge',
        'hint_en', 'hint_ge', 'answer_en', 'answer_ge', 'numeric_answer',
        'total_answers', 'correct_answers', 'has_options', 'option_1_en',
        'option_1_ge', 'option_2_en', 'option_2_ge', 'option_3_en',
        'option_3_ge','option_4_en', 'option_4_ge'
    ];

    public function wrong_answers() {
        return $this->total_answers - $this->correct_answers;
    }

    // relations

    public function categories()
    {
        return $this->belongsToMany('App\Category', 'tasks_categories',
                'task_id', 'category_id');
    }

    public function users()
    {
        return $this->belongsToMany('App\Task', 'user_tasks',
                'task_id', 'user_id')->withPivot('status')->withTimestamps();;
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
