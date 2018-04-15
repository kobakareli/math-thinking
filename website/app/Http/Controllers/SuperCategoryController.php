<?php

namespace App\Http\Controllers;

use App\SuperCategory;
use App\Category;
use Illuminate\Http\Request;

class SuperCategoryController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $categories = SuperCategory::all();
        return view('admin/supercategory/categories', [
            'categories' => $categories
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        $categories = Category::all();
        return view('admin/supercategory/createCategory', compact('categories'));
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

        $category = new SuperCategory($validatedData);

        $category->save();

        if($request['categories'] != null && count($request['categories']) > 0) {
            dd($request['categories']);
            $category->removeAllCategories();
            foreach ($request['categories'] as $cat) {
                $category->addCategory($cat);
            }
        }

        return redirect()->route('supercategories.index');
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function show(SuperCategory $superCategory)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function edit(SuperCategory $superCategory)
    {
        $categories = Category::all();
        return view('admin/supercategory/editCategory', [
            'category' => $superCategory,
            'categories' => $categories
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, SuperCategory $superCategory)
    {
        $validatedData = $request->validate([
            'title_en' => 'required',
            'title_ge' => 'required'
        ]);
        $superCategory->update($validatedData);

        $superCategory->removeAllCategories();
        if($request['categories'] != null && count($request['categories']) > 0) {
            foreach ($request['categories'] as $cat) {
                $superCategory->addCategory($cat);
            }
        }

        return redirect()->route('supercategories.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function destroy(SuperCategory $superCategory)
    {
        $superCategory->forceDelete();

        return redirect()->route('supercategories.index');
    }
}
