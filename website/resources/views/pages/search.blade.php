@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/tasks.css') }}" rel="stylesheet">
    <link href="{{ asset('css/tests.css') }}" rel="stylesheet">
    <link href="{{ asset('css/articles.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="search-page articles-page tests-page tasks-page">

        <div class="content">
            @if(isset($tasks))
                @foreach($tasks as $task)
                    <?php
                        if(Auth::check()) {
                            $user = $task->users->find(Auth::user()->id);
                            if($user != null && $user->count() > 0) {
                                $status = $user->pivot->status;
                            }
                        }
                    ?>
                    <div class="post">
                        <a href="{{ url('/task/' . $task->id) }}">
                            <p class="title fs-24 @if(isset($status) && $status == 1){{'status-passed'}}@elseif(isset($status)){{'status-failed'}}@endif">
                                {{ $task->{'title_' . App::getLocale()} }}
                            </p>
                            @foreach($task->categories as $category)
                                <span class="post-category">
                                    {{ $category->{'title_' . App::getLocale()} }}
                                </span>
                            @endforeach
                            </br>
                            <div class="text fs-16">
                                @if(strlen(strip_tags($task->{'description_' . App::getLocale()})) > 300)
                                    {{ mb_substr(strip_tags($task->{'description_' . App::getLocale()}), 0, 300, 'utf-8') }}...
                                @else
                                    {{ strip_tags($task->{'description_' . App::getLocale()}) }}
                                @endif
                            </div>
                            <p class="post-see-more">{{ trans('web.see_more') }}</p>
                        </a>
                    </div>
                    <?php
                        if(isset($status)) {
                            unset($status);
                        }
                    ?>
                @endforeach
            @endif
            @if(isset($tests))
                @foreach($tests as $test)
                    <?php
                        if(Auth::check()) {
                            $user = $test->users->find(Auth::user()->id);
                            if($user != null && $user->count() > 0) {
                                $status = $user->pivot->status;
                            }
                        }
                    ?>
                    <div class="post">
                        <a href="{{ url('/test/' . $test->id) }}">
                            <p class="title fs-24 @if(isset($status) && $status == 1){{'status-passed'}}@elseif(isset($status)){{'status-failed'}}@endif">
                                {{ $test->{'title_' . App::getLocale()} }}
                            </p>
                            @foreach($test->categories as $category)
                                <span class="post-category">
                                    {{ $category->{'title_' . App::getLocale()} }}
                                </span>
                            @endforeach
                            </br>
                            <p class="post-see-more">{{ trans('web.see_more') }}</p>
                        </a>
                    </div>
                    <?php
                        if(isset($status)) {
                            unset($status);
                        }
                    ?>
                @endforeach
            @endif
            @if(isset($articles))
                @foreach($articles as $article)
                    <div class="post">
                        <a href="{{ url('/article/' . $article->id) }}">
                            <p class="title fs-24">
                                {{ $article->{'title_' . App::getLocale()} }}
                            </p>
                            @foreach($article->categories as $category)
                                <span class="post-category">
                                    {{ $category->{'title_' . App::getLocale()} }}
                                </span>
                            @endforeach
                            </br>
                            <div class="text fs-16">
                                @if(strlen(strip_tags($article->{'description_' . App::getLocale()})) > 300)
                                    {{ mb_substr(strip_tags($article->{'description_' . App::getLocale()}), 0, 300, 'utf-8') }}...
                                @else
                                    {{ strip_tags($article->{'description_' . App::getLocale()}) }}
                                @endif
                            </div>
                            <p class="post-see-more">{{ trans('web.see_more') }}</p>
                        </a>
                    </div>
                @endforeach
            @endif
        </div>
    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/tests.js') }}"></script>
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
