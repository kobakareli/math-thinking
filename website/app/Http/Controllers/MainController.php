<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\SuperCategory;
use App\Category;

class MainController extends Controller
{
    /**
     * Fetch categories in the super category
     *
     * @param  \App\SuperCategory  $superCategory
     * @return \Illuminate\Http\Response
     */
    public function categories(SuperCategory $superCategory)
    {
        return view('renders/categoriesRender', compact('superCategory'))->render();
    }


    /**
     * Fetch categories in the super category
     *
     * @param  \App\Category  $category
     * @return \Illuminate\Http\Response
     */
    public function ajaxSearch(Category $category, $term, $datefrom=null, $dateto=null)
    {
        if($term == '-1') {
            $term = '';
        }
        if($datefrom == 'to') {
            $datefrom = null;
        }
        $tasks = $category->tasks;
        $tasksArr = array();
        $articles = $category->articles;
        $articlesArr = array();
        $tests = $category->tests;
        $testsArr = array();

        foreach($tasks as $task) {
            $created = \Carbon\Carbon::parse(explode(" ",$task->created_at)[0]);
            $from = null;
            $to = null;
            $date = true;
            if($datefrom != null) {
                $from = \Carbon\Carbon::parse($datefrom);
                $date = $from->lte($created);
            }
            if($date && $dateto != null) {
                $to = \Carbon\Carbon::parse($dateto);
                $date = $to->gte($created);
            }
            if((str_contains(strtolower($task->title_en), $term) || str_contains($task->title_ge, $term)) && $date) {
                array_push($tasksArr, $task);
                break;
            }
        }

        foreach($articles as $article) {
            $created = \Carbon\Carbon::parse(explode(" ",$article->created_at)[0]);
            $from = null;
            $to = null;
            $date = true;
            if($datefrom != null) {
                $from = \Carbon\Carbon::parse($datefrom);
                $date = $from->lte($created);
            }
            if($date && $dateto != null) {
                $to = \Carbon\Carbon::parse($dateto);
                $date = $to->gte($created);
            }
            if((str_contains(strtolower($article->title_en), $term) || str_contains($article->title_ge, $term)) && $date) {
                array_push($articlesArr, $article);
                break;
            }
        }

        foreach($tests as $test) {
            $created = \Carbon\Carbon::parse(explode(" ",$test->created_at)[0]);
            $from = null;
            $to = null;
            $date = true;
            if($datefrom != null) {
                $from = \Carbon\Carbon::parse($datefrom);
                $date = $from->lte($created);
            }
            if($date && $dateto != null) {
                $to = \Carbon\Carbon::parse($dateto);
                $date = $to->gte($created);
            }
            if((str_contains(mb_strtolower($test->title_en), $term) || str_contains($test->title_ge, $term)) && $date) {
                array_push($testsArr, $test);
                break;
            }
        }
        return view('renders/postsSearchRender', [
                    'tasks' => $tasksArr,
                    'articles' => $articlesArr,
                    'tests' => $testsArr
                ])->render();
    }
}