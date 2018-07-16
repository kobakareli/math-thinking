<?php

namespace App\Http\Controllers;

use App\Article;
use App\Category;
use App\SuperCategory;
use Illuminate\Http\Request;

class ArticleController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $articles = Article::all();
        return view('admin/article/articles', [
            'articles' => $articles
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
        return view('admin/article/createArticle', compact('categories'));
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
            'text_en' => 'required',
            'text_ge' => 'required'
        ]);

        $article = new Article($validatedData);

        $article->save();

        if($request['categories'] != null && count($request['categories']) > 0) {
            $article->removeAllCategories();
            foreach ($request['categories'] as $cat) {
                $article->addCategory($cat);
            }
        }

        return redirect()->route('articles.index');
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
        $articles = collect();
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
        else if($sort == 'category') {
            $categories = SuperCategory::orderBy('id', 'asc')->with('categories')->get();
            $subcategories = collect();
            foreach($categories as $category) {
                $subcategories = $subcategories->merge($category->categories);
            }
            $articles = collect();
            foreach($subcategories as $category) {
                $articles = $articles->merge($category->articles);
            }
        }

        if($sort != 'category') {
            $articles = Article::skip(($pageno-1)*10)->orderBy($key, $order)->take(10)->get();
            $islast = (count(Article::skip(($pageno)*10)->take(10)->get()) == 0);
        }
        else {
            $islast = (count($articles) <= $pageno*10);
            $articles = $articles->slice(($pageno-1)*10)->take(10);
        }
        $supercategories = SuperCategory::all();
        return view('pages.articles', [
            'articles' => $articles,
            'supercategories' => $supercategories,
            'pageno' => $pageno,
            'sort' => $sort,
            'islast' => $islast,
            'page_title' => 'Articles'
        ]);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Article  $article
     * @return \Illuminate\Http\Response
     */
    public function show(Article $article)
    {
        $supercategories = SuperCategory::all();
        $tasks = collect();
        foreach($article->categories as $category) {
            $tasks = $tasks->merge($category->tasks);
        }
        $moreTasks = count($tasks) > 3;
        return view('pages.article', [
            'supercategories' => $supercategories,
            'article' => $article,
            'tasks' => $tasks->take(3),
            'moretasks' => $moreTasks,
            'page_title' => $article->title_en,
            'has_share' => true
        ]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Article  $article
     * @return \Illuminate\Http\Response
     */
    public function edit(Article $article)
    {
        $categories = SuperCategory::all();
        $subcategories = collect();
        if(count($article->categories) > 0) {
            if(count($article->categories[0]->supercategories) > 0) {
                $subcategories = $article->categories[0]->supercategories[0]->categories;
            }
        }
        return view('admin/article/editArticle', [
            'article' => $article,
            'categories' => $categories,
            'subcategories' => $subcategories
        ]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Article  $article
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Article $article)
    {
        $validatedData = $request->validate([
            'title_en' => 'required',
            'title_ge' => 'required',
            'text_en' => 'required',
            'text_ge' => 'required'
        ]);

        $article->update($validatedData);

        $article->removeAllCategories();
        if($request['categories'] != null && count($request['categories']) > 0) {
            foreach ($request['categories'] as $cat) {
                $article->addCategory($cat);
            }
        }

        return redirect()->route('articles.index');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Article  $article
     * @return \Illuminate\Http\Response
     */
    public function destroy(Article $article)
    {
        $article->forceDelete();

        return redirect()->route('articles.index');
    }
}
