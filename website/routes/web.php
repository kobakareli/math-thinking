<?php

use App\SuperCategory;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    $supercategories = SuperCategory::all();
    return view('pages.index', compact('supercategories'));
});

Auth::routes();

Route::get('logout', '\App\Http\Controllers\Auth\LoginController@logout');

Route::get('/home', 'HomeController@index')->name('home');
Route::get('/tasks/page/{pageno}', 'TaskController@showAll');


// ajax

Route::get('/ajax/categories/{superCategory}', 'MainController@categories');

Route::get('/ajax/search/{category}/{term}/{datefrom?}/{dateto?}', 'MainController@ajaxSearch');


Route::middleware(['auth'])->prefix('admin')->group(function () {

    // category routes

    Route::get('/supercategories', 'SuperCategoryController@index')->name('supercategories.index');
    Route::get('/supercategories/create', 'SuperCategoryController@create');
    Route::post('/supercategories/store', 'SuperCategoryController@store');
    Route::get('/supercategories/edit/{superCategory}', 'SuperCategoryController@edit');
    Route::patch('/supercategories/update/{superCategory}', 'SuperCategoryController@update');
    Route::get('/supercategories/delete/{superCategory}', 'SuperCategoryController@destroy');

    // category routes

    Route::get('/categories', 'CategoryController@index')->name('categories.index');
    Route::get('/categories/create', 'CategoryController@create');
    Route::post('/categories/store', 'CategoryController@store');
    Route::get('/categories/edit/{category}', 'CategoryController@edit');
    Route::patch('/categories/update/{category}', 'CategoryController@update');
    Route::get('/categories/delete/{category}', 'CategoryController@destroy');


    // article routes

    Route::get('/articles', 'ArticleController@index')->name('articles.index');
    Route::get('/articles/create', 'ArticleController@create');
    Route::post('/articles/store', 'ArticleController@store');
    Route::get('/articles/edit/{article}', 'ArticleController@edit');
    Route::patch('/articles/update/{article}', 'ArticleController@update');
    Route::get('/articles/delete/{article}', 'ArticleController@destroy');


    // test routes

    Route::get('/tests', 'TestController@index')->name('tests.index');
    Route::get('/tests/create', 'TestController@create');
    Route::post('/tests/store', 'TestController@store');
    Route::get('/tests/edit/{test}', 'TestController@edit');
    Route::patch('/tests/update/{test}', 'TestController@update');
    Route::get('/tests/delete/{test}', 'TestController@destroy');
    Route::get('/ajax/tests/categories/{superCategory}', 'TestController@categories');
    Route::get('/ajax/tests/tasks/{category}', 'TestController@tasks');

    // test routes

    Route::get('/tasks', 'TaskController@index')->name('tasks.index');
    Route::get('/tasks/create', 'TaskController@create');
    Route::post('/tasks/store', 'TaskController@store');
    Route::get('/tasks/edit/{task}', 'TaskController@edit');
    Route::patch('/tasks/update/{task}', 'TaskController@update');
    Route::get('/tasks/delete/{task}', 'TaskController@destroy');

    // user routes

    Route::get('users/level/inc/{user}', 'UserController@incLevel');
    Route::get('users/levelprogress/inc/{user}/{dif}', 'UserController@incLevelProgress');
    Route::get('users/history/task/{user}/{task}/{status}', 'UserController@addTask');
    Route::get('users/history/test/{user}/{test}/{status}/{score}', 'UserController@addTest');
});
