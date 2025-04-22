<script lang='ts'>
	import { goto } from "$app/navigation";
	import { onMount } from "svelte";

    let { data } = $props();
    onMount(async () => goto(`/game/${data.params.id}/${data.params.state}`));
</script>>