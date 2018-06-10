<!doctype html>
<html lang="{{ app()->getLocale() }}">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">

        @if(isset($page_title))
            <title>{{ $page_title }} | Math Thinking</title>
            <meta class="og_title" property="og:title" content="{{ $page_title . ' | Math Thinking' }}">
        @else
            <title>Math Thinking</title>
            <meta class="og_title" property="og:title" content="Math Thinking">
        @endif

        <meta class="og_description" property="og:description" content="">

        <meta name="description"  content="" />

        <meta class="og_image" property="og:image" content="" id="meta_image">

        <meta property="og:type" content="website" />
        <meta property="fb:app_id" content="1026007677546401" />
        <meta property="fb:pages" content="" />

        <!-- Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Raleway:100,600" rel="stylesheet" type="text/css">

        <!-- CSRF Token -->
        <meta name="csrf-token" content="{{ csrf_token() }}">

        <!-- Styles -->
        <link href="{{ asset('css/app.css') }}" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet">

        <link href="{{ asset('css/main.css') }}" rel="stylesheet">
        <link href="{{ asset('css/fonts.css') }}" rel="stylesheet">

        <link href="{{ asset('css/picker.classic.css') }}" rel="stylesheet">
        <link href="{{ asset('css/picker.classic.date.css') }}" rel="stylesheet">

        @yield('styles')
    </head>
    <body>

        <div id="fb-root"></div>
        <script>
            window.fbAsyncInit = function() {
                 FB.init({
                   appId      : '1026007677546401',
                   xfbml      : true,
                   version    : 'v2.3'
                 });
            };

           (function(d, s, id){
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {return;}
                js = d.createElement(s); js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>

        <div id="app">
            <section class="header">
                <div class="nav-container">
                    <a class="logo-link" href="{{ url('/' . App::getLocale()) }}">
                        <img class="logo desktop active" src="{{ url('/images/logo.png') }}" />
                        <img class="logo mobile" src="{{ url('/images/logo.png') }}" />
                    </a>

                    <div class="nav">

                        <a href="{{ url('/' . App::getLocale() . '/articles') }}">
                            <div class="nav-item articles fs-17"><p>{{ trans('web.articles') }}</p></div>
                        </a>
                        <a href="{{ url('/' . App::getLocale() . '/tasks') }}">
                            <div class="nav-item tasks fs-17"><p>{{ trans('web.problems') }}</p></div>
                        </a>
                        <a href="{{ url('/' . App::getLocale() . '/tests') }}">
                            <div class="nav-item tests fs-17"><p>{{ trans('web.tests') }}</p></div>
                        </a>

                    </div>

                    <div class="header-buttons">
                        <div class="header-button language-switcher">
                            <div class="wrapper">
                                <?php
                                    $base = url('/');
                                    $path = request()->path();
                                    $pref = substr($path, 0, 2);
                                    if($pref == 'en' || $pref == 'ru' || $pref == 'ge') {
                                        $path = substr($path, 2);
                                        if(substr($path, 0, 1) == '/') {
                                            $path = substr($path, 1);
                                        }
                                    }
                                ?>
                                <div class="lang en <?php if(app()->getLocale() == 'en' || app()->getLocale() == null) { echo 'active'; } ?>">
                                    <a href="{{ $base . '/en/' . $path }}">
                                        <p class="lang-text fs-16">EN</p>
                                    </a>
                                </div>
                                <div class="lang ge <?php if(app()->getLocale() == 'ge') { echo 'active'; } ?>">
                                    <a href="{{ $base . '/ge/' . $path }}">
                                        <p class="lang-text fs-16">GE</p>
                                    </a>
                                </div>
                            </div>
                        </div>

                        <div class="header-button search-button">
                        </div>
                    </div>
                </div>
                <div class="search-wrapper search-id">
                    <div class="black-filter">
                    </div>
                    <div class="search-container">
                        <div class="selects">
                            <select type="select" class="select" name="super-category" id="super-category">
                                @foreach($supercategories as $category)
                                    <option value="{{ $category->id }}">{{ $category->{'title_' . App::getLocale()} }}</option>
                                @endforeach
                            </select>
                            <div class="categories-wrapper">
                                <select type="select" class="select" name="categories" id="categories">
                                </select>
                            </div>
                            <div class="dummy" style="display: none;"></div>
                            <input id="date-from" class="datepicker" type="date" name="date-from" placeholder="{{ trans('web.start_date') }}"/>
                            <input id="date-to" class="datepicker" type="date" name="date-to" placeholder="{{ trans('web.end_date') }}"/>
                        </div>
                        <div class="search-input">
                            <input class="fs-26" type="text" name="search" placeholder="{{ trans('web.search') }}"/>
                        </div>
                        <div class="results active">

                        </div>
                    </div>
                </div>
            </section>

            <div class="left-content">
                @if (Auth::check())
                    <div class="logged-in active">
                        <p class="greet fs-20">{{ trans('web.hello') }}, <span class="user-name">{{ Auth::user()->name }}</span></p>

                        <a href="{{ url('/user') }}" class="profile-link link fs-17">
                            {{ trans('web.profile') }}
                        </a>
                        <a href="/logout" class="logout-link link fs-17">
                            {{ trans('web.logout') }}
                        </a>
                    </div>
                @else
                    <form class="login-form active" method="post" action="/login">

                        {{ csrf_field() }}

                        <div class="input-parent">
                            <input type="text" name="email" placeholder="{{ trans('web.email') }}" required>
                        </div>

                        <div class="input-parent">
                            <input type="password" name="password" placeholder="{{ trans('web.password') }}" required>
                        </div>


                        <button type="submit" class="sign-in-button fs-17">{{ trans('web.signin') }}</button>

                        <a href="/register" class="register-link link fs-17">
                            {{ trans('web.register') }}
                        </a>
                        <a href="/password/reset" class="password-recovery-link link fs-17">
                            {{ trans('web.recover') }}
                        </a>
                    </form>
                @endif
            </div>

            <div class="central-content">
                @yield('content')
            </div>

            <div class="right-content">

                @if (isset($has_share))
                    <div class="share-div">
                        <p class="share fs-18">{{ trans('web.share_page') }}:</p>
                        <img class="fb-button" src="/images/fb.png">
                    </div>

                    <hr>
                @endif

                <div class="trending">
                    <p class="blog-section-title fs-20">{{ trans('web.trending') }} {{ trans('web.problems') }}</p>

                    <div class="trends">
                        @foreach($popular as $ptask)
                            <a href="{{ url('/tasks/' . $ptask->id) }}">
                                <div class="trend">

                                    <p class="title fs-16">
                                        {{ $ptask->{'title_' . App::getLocale()} }}
                                    </p>
                                    <div class="info">
                                        <span class="category fs-15">
                                            @if(count($ptask->categories))
                                                {{ $ptask->categories[0]->{'title_' . App::getLocale()} }}
                                            @endif
                                        </span>
                                        <div class="circle">
                                        </div>
                                        <span class="date fs-15">
                                            {{ \Carbon\Carbon::parse(explode(" ",$ptask->created_at)[0])->format('d/m/Y') }}
                                        </span>
                                    </div>
                                </div>
                            </a>
                        @endforeach
                    </div>
                </div>
            </div>

        </div>

        @stack('scripts')
        <script type="text/javascript" src="{{ asset('js/jquery.min.js') }}"></script>
        <!--<script type="text/javascript" src="{{ asset('js/app.js') }}"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
        <script type="text/javascript" src="{{ asset('js/main.js') }}"></script>
        <script type="text/javascript" src="{{ asset('js/picker.js') }}"></script>
        <script type="text/javascript" src="{{ asset('js/picker.date.js') }}"></script>

        @yield('scripts')
    </body>
</html>
