@extends('layouts.template')

@section('styles')
    <link href="{{ asset('css/tasks.css') }}" rel="stylesheet">
@endsection

@section('content')
    <div class="tasks-page">

        <select type="select" class="select" name="sort" id="sort" placeholder="{{ trans('web.newest') }}" data-url="{{ url('/') }}">
            <option default value="new">{{ trans('web.newest') }}</option>
            <option value="old">{{ trans('web.oldest') }}</option>
            <option value="az">{{ trans('web.az') }}</option>
            <option value="za">{{ trans('web.za') }}</option>
            <option value="ma">{{ trans('web.most_answered') }}</option>
            <option value="la">{{ trans('web.least_answered') }}</option>
            <option value="mc">{{ trans('web.most_correct') }}</option>
            <option value="lc">{{ trans('web.least_correct') }}</option>
        </select>

        <div class="content">
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

            <div class="pagination">
                @if($pageno > 1)
                    <a class="pagination-link prev fs-18" href="{{ url('/tasks/' . ($pageno-1)) . '/' . $sort}}">{{ trans('web.previous') }}</a>
                @endif
                @if(!$islast)
                    <a class="pagination-link next fs-18" href="{{ url('/tasks/' . ($pageno+1)) . '/' . $sort}}">{{ trans('web.next') }}</a>
                @endif
            </div>
        </div>
    </div>
@endsection

@section('scripts')
    <script type="text/javascript" src="{{ asset('js/tasks.js') }}"></script>
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
