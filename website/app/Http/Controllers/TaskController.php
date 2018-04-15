<?php

namespace App\Http\Controllers;

use App\Task;
use App\SuperCategory;
use App\Category;
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
            if(isset($request['answer_en'])) {
                $validatedData['answer_en'] = $request['answer_en'];
            }
            if(isset($request['answer_ge'])) {
                $validatedData['answer_ge'] = $request['answer_ge'];
            }
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
        //
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
        $subcategories = $task->categories[0]->supercategories[0]->categories;
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
            if(isset($request['answer_en'])) {
                $validatedData['answer_en'] = $request['answer_en'];
            }
            if(isset($request['answer_ge'])) {
                $validatedData['answer_ge'] = $request['answer_ge'];
            }
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
