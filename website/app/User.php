<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Auth;

class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name', 'email', 'password', 'level', 'level_progress'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token',
    ];


    public function tasks()
    {
        return $this->belongsToMany('App\Task', 'user_tasks',
                'user_id', 'task_id')->withPivot('status')->withTimestamps();
    }

    public function tasksHistory()
    {
        return $this->belongsToMany('App\Task', 'user_tasks_history',
                'user_id', 'task_id')->withPivot('status', 'submitted_answer')->withTimestamps()->orderBy('pivot_created_at', 'DESC');
    }

    public function tests()
    {
        return $this->belongsToMany('App\Test', 'user_tests',
                'user_id', 'test_id')->withPivot('status', 'score')->withTimestamps();
    }

    public function testsHistory()
    {
        return $this->belongsToMany('App\Test', 'user_tests_history',
                'user_id', 'test_id')->withPivot('status', 'score')->withTimestamps()->orderBy('pivot_created_at', 'DESC');
    }

    public function addTask($task_id, $status)
    {
        $this->tasks()->detach($task_id);
        $this->tasks()->attach($task_id, ['status' => $status]);
    }

    public function removeTask($task_id)
    {
        $this->tasks()->detach($task_id);
    }

    public function addTaskHistory($task_id, $status, $answer)
    {
        $this->tasksHistory()->attach($task_id, ['status' => $status, 'submitted_answer' => $answer]);
    }

    public function getTaskHistory($task_id) {
        return $this->tasksHistory()->orderBy('created_at', 'DESC')->where('task_id', $task_id)->where('user_id', auth()->user()->id)->get();
    }

    public function addTest($test_id, $status, $score)
    {
        $this->tests()->detach($test_id);
        $this->tests()->attach($test_id, ['status' => $status, 'score' => $score]);
    }

    public function removeTest($test_id)
    {
        $this->tests()->detach($test_id);
    }

    public function addTestHistory($test_id, $status, $score)
    {
        $this->testsHistory()->attach($test_id, ['status' => $status, 'score' => $score]);
    }

    public function getTestHistory($test_id) {
        return $this->testsHistory()->orderBy('created_at', 'DESC')->where('test_id', $test_id)->where('user_id', auth()->user()->id)->get();
    }
}
