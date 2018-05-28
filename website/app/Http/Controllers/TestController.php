<?php

namespace App\Http\Controllers;

use App\Test;
use App\SuperCategory;
use App\Category;
use App\Task;
use Illuminate\Http\Request;
use Auth;

class TestController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $tests = Test::all();
        return view('admin/test/tests', [
            'tests' => $tests
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
        return view('admin/test/createTest', compact('categories'));
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
            'title_ge' => 'required'
        ]);

        $test = new Test($validatedData);

        $test->save();

        if($request['categories'] != null && count($request['categories']) > 0) {
            $test->removeAllCategories();
            foreach ($request['categories'] as $cat) {
                $test->addCategory($cat);
            }
        }

        if($request['tasks'] != null && count($request['tasks']) > 0) {
            $test->removeAllTasks();
            foreach ($request['tasks'] as $task) {
                $test->addTask($task);
            }
        }

        return redirect()->route('tests.index');
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Test  $test
     * @return \Illuminate\Http\Response
     */
    public function show(Test $test)
    {
        $supercategories = SuperCategory::all();
        if(Auth::check()) {
            $t = auth()->user()->tests()->find($test->id);
            if($t != null) {
                $score = $t->pivot->score;
                $status = $t->pivot->status;
                return view('pages.test', compact('supercategories', 'test', 'status', 'score'));
            }
        }
        return view('pages.test', compact('supercategories', 'test'));
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
        $tests = Test::skip(($pageno-1)*10)->orderBy($key, $order)->take(10)->get();
        $islast = (count(Test::skip(($pageno)*10)->take(10)->get()) == 0);
        $supercategories = SuperCategory::all();
        return view('pages.tests', [
            'tests' => $tests,
            'supercategories' => $supercategories,
            'pageno' => $pageno,
            'sort' => $sort,
            'islast' => $islast
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Test  $test
     * @return \Illuminate\Http\Response
     */
    public function edit(Test $test)
    {
        $categories = SuperCategory::all();
        $subcategories = $test->categories[0]->supercategories[0]->categories;
        return view('admin/test/editTest', [
            'test' => $test,
            'categories' => $categories,
            'subcategories' => $subcategories
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Test  $test
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Test $test)
    {
        $validatedData = $request->validate([
            'title_en' => 'required',
            'title_ge' => 'required'
        ]);

        $test->update($validatedData);

        $test->removeAllCategories();
        if($request['categories'] != null && count($request['categories']) > 0) {
            foreach ($request['categories'] as $cat) {
                $test->addCategory($cat);
            }
        }

        $test->removeAllTasks();
        if($request['tasks'] != null && count($request['tasks']) > 0) {
            foreach ($request['tasks'] as $task) {
                $test->addTask($task);
            }
        }

        return redirect()->route('tests.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Test  $test
     * @return \Illuminate\Http\Response
     */
    public function destroy(Test $test)
    {
        $test->forceDelete();

        return redirect()->route('tests.index');
    }

    /**
     * Fetch categories in the super category
     *
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function categories(SuperCategory $superCategory)
    {
        return view('renders/TestCategories', compact('superCategory'))->render();
    }

    /**
     * Fetch tasks in the category
     *
     * @param  \App\Category  $category
     * @return \Illuminate\Http\Response
     */
    public function tasks(Category $category)
    {
        return view('renders/TestTasks', compact('category'))->render();
    }


    /**
     * Fetch tasks in the category
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function submitTest(Request $request, Test $test)
    {
        $answers = $request->all();
        $answers = array_slice($answers, 1);
        $count = 0;
        $tasks = $test->tasks;
        foreach($answers as $key=>$value) {
            $id = explode('-', $key)[1];
            $task = $tasks->find($id);
            if($task->numeric_answer == $value) {
                $count += 1;
            }
        }
        auth()->user()->addTest($test->id, 1, $count);
        auth()->user()->addTestHistory($test->id, 1, $count);
        return redirect('/test/' . $test->id);
    }
}
