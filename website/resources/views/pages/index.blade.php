@extends('layouts.template')

@section('styles')
@endsection

@section('content')
    <p class="title fs-28">{{ trans('web.website_title') }}</p>
    <div class="content fs-22">
        {{ trans('web.website_description') }}
    </div>
@endsection

@section('scripts')
@endsection

@push('scripts')
    <script>
        var superCategories = @json($supercategories);
    </script>
@endpush
