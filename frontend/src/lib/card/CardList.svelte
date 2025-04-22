<script lang='ts'>
    import Card from '$lib/card/Card.svelte';
    interface Props {
        cards: any;
    }

    let { cards }: Props = $props();
</script>

{#if cards}
    {#each cards as card}
        <Card {card}/>
    {/each}
{/if}