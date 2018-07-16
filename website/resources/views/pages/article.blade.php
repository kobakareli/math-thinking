@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/task.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="article-page task-page">

        <p class="title fs-28">{!! $article->{'title_' . App::getLocale()} !!}</p>
        <div class="description content active fs-18">
            {!! $article->{'text_' . App::getLocale()} !!}
        </div>

        <div class="trending article-tasks">
            <span class="blog-section-title fs-20">{{ trans('web.problems') }}</span>
            <a href="{{ url('/' . App::getLocale() . '/search/tasks/-1/' . $article->categories[0]->id) }}" class="see-more-link fs-20">{{ trans('web.see_more') }}</a>
            <div class="trends">
                @foreach($tasks as $ptask)
                    <a href="{{ url('/' . App::getLocale() . '/task/' . $ptask->id) }}">
                        <div class="trend">

                            <p class="title fs-16">
                                {{ $ptask->{'title_' . App::getLocale()} }}
                            </p>
                            <div class="info">
                                <span class="date fs-15">
                                    {{ \Carbon\Carbon::parse(explode(" ",$ptask->created_at)[0])->format('d/m/Y') }}
                                </span>
                            </div>
                        </div>
                    </a>
                @endforeach
            </div>
        </div>

        <div class="fb-comments" data-href="{{ url('/' . App::getLocale() . '/article/' . $article->id) }}" data-numposts="5"></div>

    </div>
@endsection
