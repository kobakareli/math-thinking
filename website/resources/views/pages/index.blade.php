@extends('layouts.template')

@section('styles')
@endsection

@section('content')
    <p class="title fs-28">{{ trans('web.website_title') }}</p>
    <div class="content fs-22">
        {{ trans('web.website_description') }}

        @if(count($articles) > 0)
            <div class="trending article-tasks">
                <span class="blog-section-title fs-20">{{ trans('web.new_articles') }}</span>
                <a href="{{ url('/' . App::getLocale() . '/articles') }}" class="see-more-link fs-20">{{ trans('web.see_more') }}</a>
                <div class="trends">
                    @foreach($articles as $article)
                        <a href="{{ url('/' . App::getLocale() . '/article/' . $article->id) }}">
                            <div class="trend">

                                <p class="title fs-16">
                                    {{ $article->{'title_' . App::getLocale()} }}
                                </p>
                                <div class="info">
                                    <span class="date fs-15">
                                        {{ \Carbon\Carbon::parse(explode(" ",$article->created_at)[0])->format('d/m/Y') }}
                                    </span>
                                </div>
                            </div>
                        </a>
                    @endforeach
                </div>
            </div>
        @endif
    </div>
@endsection

@section('scripts')
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
