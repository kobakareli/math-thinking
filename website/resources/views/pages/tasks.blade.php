@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/tasks.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="tasks-page">
        @foreach($tasks as $task)
            <div class="post">
                <a href="{{ url('/tasks/' . $task->id) }}">
                    <p class="title fs-24">
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
        @endforeach
        <div class="pagination">
            @if($pageno > 1)
                <a class="pagination-link prev fs-18" href="{{ url('/tasks/page/' . ($pageno-1)) }}">{{ trans('web.previous') }}</a>
            @endif
            <a class="pagination-link next fs-18" href="{{ url('/tasks/page/' . ($pageno+1)) }}">{{ trans('web.next') }}</a>
        </div>
    </div>
@endsection

@section('scripts')
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
