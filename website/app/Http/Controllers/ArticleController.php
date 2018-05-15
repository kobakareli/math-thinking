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
        $categories = Category::all();
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

        $articles = Article::skip(($pageno-1)*10)->orderBy($key, $order)->take(10)->get();
        $islast = (count(Article::skip(($pageno)*10)->take(10)->get()) > 0);
        $supercategories = SuperCategory::all();
        return view('pages.articles', [
            'articles' => $articles,
            'supercategories' => $supercategories,
            'pageno' => $pageno,
            'sort' => $sort,
            'islast' => $islast
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
        return view('pages.article', compact('supercategories', 'article'));
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Article  $article
     * @return \Illuminate\Http\Response
     */
    public function edit(Article $article)
    {
        $categories = Category::all();
        return view('admin/article/editArticle', [
            'article' => $article,
            'categories' => $categories
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
