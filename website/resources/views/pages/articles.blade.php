@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/tasks.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="articles-page tasks-page">

        <select type="select" class="select" name="sort" id="sort" placeholder="{{ trans('web.newest') }}" data-url="{{ url('/') }}">
            <option default value="new">{{ trans('web.newest') }}</option>
            <option value="old">{{ trans('web.oldest') }}</option>
            <option value="az">{{ trans('web.az') }}</option>
            <option value="za">{{ trans('web.za') }}</option>
        </select>

        <div class="content">
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

            <div class="pagination">
                @if($pageno > 1)
                    <a class="pagination-link prev fs-18" href="{{ url('/articles/' . ($pageno-1)) . '/' . $sort}}">{{ trans('web.previous') }}</a>
                @endif
                <a class="pagination-link next fs-18" href="{{ url('/articles/' . ($pageno+1)) . '/' . $sort}}">{{ trans('web.next') }}</a>
            </div>
        </div>
    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/articles.js') }}"></script>
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
