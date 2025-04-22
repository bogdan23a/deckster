<script lang='ts'>
	import GameTask from '$lib/game/GameTask.svelte';
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';
	import { Client } from '@stomp/stompjs';

    let { data } = $props();

    const sleep = (time: number) => new Promise(resolve => setTimeout(resolve, time));

    const refresh = async () => {
        await sleep(100).then(() => {
            goto(`/refresh/${data.game.id}/${data.game.state}`);
        });
    };
    onMount(() => {
        const client = new Client({
            brokerURL: `${import.meta.env.VITE_DECKMASTER_WEBSOCKET_PROTOCOL}://${import.meta.env.VITE_DECKMASTER_URI}/websocket`,
        });

        client.onConnect = (frame) => {
            client.subscribe('/public', refresh)
        }

        client.onStompError = (frame) => {
            console.log("error", frame)
        }

        client.activate();
    });
</script>

<form method="POST" action="/game">
    <GameTask task={data.task}/>
</form>