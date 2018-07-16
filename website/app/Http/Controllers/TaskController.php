<?php

namespace App\Http\Controllers;

use App\Task;
use App\User;
use App\SuperCategory;
use App\Category;
use App\Calculator;
use Illuminate\Http\Request;

class TaskController extends Controller
{

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $tasks = Task::all();
        return view('admin/task/tasks', [
            'tasks' => $tasks
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $categories = SuperCategory::all();
        return view('admin/task/createTask', compact('categories'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $validatedData = $request->validate([
            'title_en' => 'required',
            'title_ge' => 'required',
            'description_en' => 'required',
            'description_ge' => 'required',
            'numeric_answer' => 'required'
        ]);

        if($request['has_options']) {
            $validatedData['has_options'] = 1;
            if(isset($request['option_1_en'])) {
                $validatedData['option_1_en'] = $request['option_1_en'];
            }
            if(isset($request['option_2_en'])) {
                $validatedData['option_2_en'] = $request['option_2_en'];
            }
            if(isset($request['option_3_en'])) {
                $validatedData['option_3_en'] = $request['option_3_en'];
            }
            if(isset($request['option_4_en'])) {
                $validatedData['option_4_en'] = $request['option_4_en'];
            }
            if(isset($request['option_1_ge'])) {
                $validatedData['option_1_ge'] = $request['option_1_ge'];
            }
            if(isset($request['option_2_ge'])) {
                $validatedData['option_2_ge'] = $request['option_2_ge'];
            }
            if(isset($request['option_3_ge'])) {
                $validatedData['option_3_ge'] = $request['option_3_ge'];
            }
            if(isset($request['option_4_ge'])) {
                $validatedData['option_4_ge'] = $request['option_4_ge'];
            }
        }
        else {
            $validatedData['has_options'] = 0;
        }

        if(isset($request['answer_en'])) {
            $validatedData['answer_en'] = $request['answer_en'];
        }
        if(isset($request['answer_ge'])) {
            $validatedData['answer_ge'] = $request['answer_ge'];
        }

        if(isset($request['hint_en'])) {
            $validatedData['hint_en'] = $request['hint_en'];
        }
        if(isset($request['hint_ge'])) {
            $validatedData['hint_ge'] = $request['hint_ge'];
        }

        $task = new Task($validatedData);

        $task->save();


        if($request['categories'] != null && count($request['categories']) > 0) {
            $task->removeAllCategories();
            foreach ($request['categories'] as $cat) {
                $task->addCategory($cat);
            }
        }

        return redirect()->route('tasks.index');
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Task  $task
     * @return \Illuminate\Http\Response
     */
    public function show(Task $task)
    {
        $supercategories = SuperCategory::all();
        return view('pages.task', [
            'supercategories' => $supercategories,
            'task' => $task,
            'page_title' => $task->title_en,
            'has_share' => true
        ]);
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showAll($pageno=null, $sort=null)
    {
        if($pageno == null) {
            $pageno = 1;
        }
        if($sort == null) {
            $sort = 'newest';
        }
        $key = 'created_at';
        $order = 'desc';
        $tasks = collect();
        if($sort == 'old') {
            $order = 'asc';
        }
        else if($sort == 'az') {
            $key = 'title_' . app()->getLocale();
            $order = 'asc';
        }
        else if($sort == 'za') {
            $key = 'title_' . app()->getLocale();
            $order = 'desc';
        }
        else if($sort == 'ma') {
            $key = 'total_answers';
            $order = 'desc';
        }
        else if($sort == 'la') {
            $key = 'total_answers';
            $order = 'asc';
        }
        else if($sort == 'mc') {
            $key = 'correct_answers';
            $order = 'desc';
        }
        else if($sort == 'lc') {
            $key = 'correct_answers';
            $order = 'asc';
        }
        else if($sort == 'category') {
            $categories = SuperCategory::orderBy('id', 'asc')->with('categories')->get();
            $subcategories = collect();
            foreach($categories as $category) {
                $subcategories = $subcategories->merge($category->categories);
            }
            $tasks = collect();
            foreach($subcategories as $category) {
                $tasts = $tasks->merge($category->tasks);
            }
        }

        if($sort != 'category') {
            $tasks = Task::skip(($pageno-1)*10)->orderBy($key, $order)->take(10)->get();
            $islast = (count(Task::skip(($pageno)*10)->take(10)->get()) == 0);
        }
        else {
            $islast = (count($tasks) <= $pageno*10);
            $tasks = $tasks->slice(($pageno-1)*10)->take(10);
        }
        $supercategories = SuperCategory::all();
        return view('pages.tasks', [
            'tasks' => $tasks,
            'supercategories' => $supercategories,
            'pageno' => $pageno,
            'sort' => $sort,
            'islast' => $islast,
            'page_title' => 'Tasks'
        ]);
    }

    /**
     * Submit answer to question.
     *
     * @param  \Illuminate\Http\Request  $request
     */
    public function submit(Request $request)
    {
        $data = $request->all();
        $answer = $data['answer'];
        $answer = Calculator::calculate($answer);
        $iscorrect = 0;
        if($answer != "error") {
            $user = User::find($data['user']);
            $task = Task::find($data['task']);
            if($answer == $task->numeric_answer) {
                $iscorrect = 1;
                if(!$user->tasks->contains($data['task'])) {
                    if($user->level_progress == $user->level - 1) {
                        $user->level += 1;
                        $user->level_progress = 0;
                        $iscorrect = 2;
                    }
                    else {
                        $user->level_progress += 1;
                    }
                    $user->update();
                }
            }
            if($iscorrect > 0) {
                $user->addTask($task->id, $iscorrect);
                $user->addTaskHistory($task->id, 1, $answer);
                $task->correct_answers += 1;
            }
            else {
                $user->addTask($task->id, $iscorrect);
                $user->addTaskHistory($task->id, 0, $answer);
            }
            $task->total_answers += 1;
            $task->update();
        }
        return $iscorrect;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Task  $task
     * @return \Illuminate\Http\Response
     */
    public function edit(Task $task)
    {
        $categories = SuperCategory::all();
        $subcategories = collect();
        if(count($task->categories) > 0) {
            if(count($task->categories[0]->supercategories) > 0) {
                $subcategories = $task->categories[0]->supercategories[0]->categories;
            }
        }
        return view('admin/task/editTask', [
            'task' => $task,
            'categories' => $categories,
            'subcategories' => $subcategories
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Task  $task
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Task $task)
    {
        $validatedData = $request->validate([
            'title_en' => 'required',
            'title_ge' => 'required',
            'description_en' => 'required',
            'description_ge' => 'required',
            'numeric_answer' => 'required'
        ]);

        if($request['has_options']) {
            $validatedData['has_options'] = 1;
            if(isset($request['option_1_en'])) {
                $validatedData['option_1_en'] = $request['option_1_en'];
            }
            if(isset($request['option_2_en'])) {
                $validatedData['option_2_en'] = $request['option_2_en'];
            }
            if(isset($request['option_3_en'])) {
                $validatedData['option_3_en'] = $request['option_3_en'];
            }
            if(isset($request['option_4_en'])) {
                $validatedData['option_4_en'] = $request['option_4_en'];
            }
            if(isset($request['option_1_ge'])) {
                $validatedData['option_1_ge'] = $request['option_1_ge'];
            }
            if(isset($request['option_2_ge'])) {
                $validatedData['option_2_ge'] = $request['option_2_ge'];
            }
            if(isset($request['option_3_ge'])) {
                $validatedData['option_3_ge'] = $request['option_3_ge'];
            }
            if(isset($request['option_4_ge'])) {
                $validatedData['option_4_ge'] = $request['option_4_ge'];
            }
        }
        else {
            $validatedData['has_options'] = 0;
        }

        if(isset($request['answer_en'])) {
            $validatedData['answer_en'] = $request['answer_en'];
        }
        if(isset($request['answer_ge'])) {
            $validatedData['answer_ge'] = $request['answer_ge'];
        }

        if(isset($request['hint_en'])) {
            $validatedData['hint_en'] = $request['hint_en'];
        }
        if(isset($request['hint_ge'])) {
            $validatedData['hint_ge'] = $request['hint_ge'];
        }
        $task->update($validatedData);


        if($request['categories'] != null && count($request['categories']) > 0) {
            $task->removeAllCategories();
            foreach ($request['categories'] as $cat) {
                $task->addCategory($cat);
            }
        }

        return redirect()->route('tasks.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Task  $task
     * @return \Illuminate\Http\Response
     */
    public function destroy(Task $task)
    {
        $task->forceDelete();

        return redirect()->route('tasks.index');
    }
}
