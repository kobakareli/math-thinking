@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/tasks.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="tests-page tasks-page">

        <select type="select" class="select" name="sort" id="sort" placeholder="{{ trans('web.newest') }}" data-url="{{ url('/' . App::getLocale()) }}">
            <option @if($sort == 'new'){{ "selected" }}@endif default value="new">{{ trans('web.newest') }}</option>
            <option @if($sort == 'old'){{ "selected" }}@endif value="old">{{ trans('web.oldest') }}</option>
            <option @if($sort == 'az'){{ "selected" }}@endif value="az">{{ trans('web.az') }}</option>
            <option @if($sort == 'za'){{ "selected" }}@endif value="za">{{ trans('web.za') }}</option>
            <option @if($sort == 'category'){{ "selected" }}@endif value="category">{{ trans('web.category_sort') }}</option>
        </select>

        <div class="content">
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
                    <a href="{{ url('/' . App::getLocale() . '/test/' . $test->id) }}">
                        <p class="title fs-24 @if(isset($status) && $status == 1){{'status-passed'}}@elseif(isset($status)){{'status-failed'}}@endif">
                            {{ $test->{'title_' . App::getLocale()} }}
                        </p>
                        <!--@foreach($test->categories as $category)
                            <span class="post-category">
                                {{ $category->{'title_' . App::getLocale()} }}
                            </span>
                        @endforeach-->
                        <span class="post-category">
                            {{ $test->categories[0]->supercategories[0]->{'title_' . App::getLocale()} }}
                        </span>
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

            <div class="pagination">
                @if($pageno > 1)
                    <a class="pagination-link prev fs-18" href="{{ url('/tests/' . ($pageno-1)) . '/' . $sort}}">{{ trans('web.previous') }}</a>
                @endif
                @if(!$islast)
                    <a class="pagination-link next fs-18" href="{{ url('/tests/' . ($pageno+1)) . '/' . $sort}}">{{ trans('web.next') }}</a>
                @endif
            </div>
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
