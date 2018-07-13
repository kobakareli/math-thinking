<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use Illuminate\Support\Facades\Schema;

use App\SuperCategory;
use App\Task;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        //
        Schema::defaultStringLength(191);

        //if(Schema::hasTable('suer_categories')) {
        view()->share('supercategories', SuperCategory::all());
        //}
        //if(Schema::hasTable('tasks')) {
        $tasks = Task::orderBy('total_answers', 'DESC')->take(5)->get();
        view()->share('popular', $tasks);
        //}
    }

    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }
}
