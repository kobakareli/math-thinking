<?php

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
    return view('welcome');
});

Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::middleware(['auth'])->prefix('admin')->group(function () {

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
});
