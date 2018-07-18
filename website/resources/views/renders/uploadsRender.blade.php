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
