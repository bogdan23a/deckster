<script>
    let { id, title, nextState } = $props();
</script>

<h2>{title}</h2>
<p>{id}</p>
<button class="variant-filled-error deleteButton">{nextState}</button>