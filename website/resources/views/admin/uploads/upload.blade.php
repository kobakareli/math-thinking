@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">Upload image</div>

                <div class="panel-body">

                    @if(isset($url))
                        <span class="language-marker">Uploaded image url:</span>

                        <strong><p>{{ $url }}</p></strong>
                    @endif

                    <form method="POST" enctype="multipart/form-data" action="/admin/image/store">

                        {{ csrf_field() }}

                        <span class="language-marker">Upload an image, then use the displayed url to insert the image into editor:</span>

                        <div class="form-group">
                            <input type="file" name="cover_photo" class="form-control about-title"></input>
                            <span class="language-marker">Recommended resolution: 1920 x 1080</span><br>
                            <span class="language-marker">Maximum size: 2MB</span><br>
                            <span class="language-marker">Allowed types: jpg,bmp,png,svg</span><br>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary pull-right">Upload</button>
                        </div>

                    </form>

                    @include('admin/errors')

                </div>

                <div class="panel-body uploads" data-url="{{ url('/admin/image/ajax/') }}">
                    <span class="language-marker" style="margin-bottom: 20px; display: inline-block;">List of all the uploaded images:</span>

                    @foreach($files as $image)
                        @if($image != '')
                            <div class="form-group">
                                <img class="pull-left" style="margin-bottom: 20px; width: 90%" src="{{ $image }}" /img>
                                <a class="copy pull-right" style="margin-bottom: 20px; margin-left: 20px; display: block;" href="{{ url($image) }}">Copy</a>

                                <?php
                                    $arr = explode("/",$image);
                                    $count = count($arr);
                                    $name = $arr[$count - 1];
                                ?>
                                <a class="deleteImage pull-right" href="{{ url('/admin/image/delete/' . $name) }}">Delete</a>
                            </div>
                        @endif
                    @endforeach
                </div>

            </div>
        </div>
    </div>
</div>
@endsection
