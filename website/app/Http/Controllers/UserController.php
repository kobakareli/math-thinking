<?php

namespace App\Http\Controllers;

use App\Task;
use App\Test;
use App\User;
use Illuminate\Http\Request;

class UserController extends Controller
{

    public function incLevel(User $user)
    {
        $user->level += 1;
        $user->update();
    }

    public function incLevelProgress(User $user, $dif)
    {
        $user->level_progress += dif;
        $user->update();
    }

    // add task in user history
    public function addTask(User $user, Task $task, $status)
    {
        if(!$user->tasks->contains($task->id)) {
            $user->addTask($task->id, $status);
        }
        else {
            foreach($user->tasks as $t) {
                if($t->id == $task->id && $t->pivot->status != $status) {
                    $user->removeTask($task->id);
                    $user->addTask($task->id, $status);
                    return;
                }
            }
        }
    }

    // add test in user history
    public function addTest(User $user, Test $test, $status, $score)
    {
        if(!$user->tests->contains($test->id)) {
            $user->addTest($test->id, $status, $score);
        }
        else {
            foreach($user->tests as $t) {
                if($t->id == $test->id && ($t->pivot->status != $status || $t->pivot->score != $score )) {
                    $user->removeTask($test->id);
                    $user->addTest($test->id, $status, $score);
                    return;
                }
            }
        }
    }

    public function showProfile()
    {
        return view('pages.userProfile');
    }
}
