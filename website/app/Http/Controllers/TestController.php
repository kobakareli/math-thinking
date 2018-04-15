<?php

namespace App\Http\Controllers;

use App\Test;
use App\SuperCategory;
use App\Category;
use App\Task;
use Illuminate\Http\Request;

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
        //
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
}