<?php

namespace App\Providers;

use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Support\Providers\RouteServiceProvider as ServiceProvider;

use Illuminate\Routing\Router;
use Illuminate\Http\Request;

use App\SuperCategory;
use App\Task;

class RouteServiceProvider extends ServiceProvider
{
    /**
     * This namespace is applied to your controller routes.
     *
     * In addition, it is set as the URL generator's root namespace.
     *
     * @var string
     */
    protected $namespace = 'App\Http\Controllers';

    /**
     * Define your route model bindings, pattern filters, etc.
     *
     * @return void
     */
    public function boot()
    {
        //

        parent::boot();
        view()->share('supercategories', SuperCategory::all());
        $tasks = Task::orderBy('total_answers', 'DESC')->take(5)->get();
        view()->share('popular', $tasks);
    }

    /**
     * Define the routes for the application.
     *
     * @return void
     */
    public function map(Router $router, Request $request)
    {
        $this->mapApiRoutes();

        $this->mapWebRoutes();

        //
        $locale = $request->segment(1);
        if($locale != 'login' && $locale != 'register' && $locale != 'admin' && $locale != 'home') {
            if($locale != 'en' && $locale != 'ge') {
                $locale = 'en';
            }
            $this->app->setLocale($locale);

            $router->group(['namespace' => $this->namespace, 'prefix' => $locale, 'middleware' => 'web'], function($router) {
                require app_path('../routes/web.php');
            });
        }
    }

    /**
     * Define the "web" routes for the application.
     *
     * These routes all receive session state, CSRF protection, etc.
     *
     * @return void
     */
    protected function mapWebRoutes()
    {
        Route::middleware('web')
             ->namespace($this->namespace)
             ->group(base_path('routes/web.php'));
    }

    /**
     * Define the "api" routes for the application.
     *
     * These routes are typically stateless.
     *
     * @return void
     */
    protected function mapApiRoutes()
    {
        Route::prefix('api')
             ->middleware('api')
             ->namespace($this->namespace)
             ->group(base_path('routes/api.php'));
    }
}
