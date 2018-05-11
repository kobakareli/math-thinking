@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/task.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="article-page task-page">

        <p class="title fs-28">{!! $article->{'title_' . App::getLocale()} !!}</p>
        <div class="descriptio content active fs-18">
            {!! $article->{'text_' . App::getLocale()} !!}
        </div>

    </div>
@endsection
