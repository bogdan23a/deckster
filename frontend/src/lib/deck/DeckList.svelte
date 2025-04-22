<script lang='ts'>
    import Deck from '$lib/deck/Deck.svelte';
    let { decks } = $props();
</script>

<!-- <tr class="grid grid-cols-5 gap-1"> -->
    <select name="deckId">
        {#each decks as deck}
            <!-- {console.log(deck.name)} -->
            <option value={deck.id}>
                <!-- <td> -->
                {deck.name}    
                <!-- <Deck {deck}/> -->
                <!-- </td> -->
            </option>
        {/each}
    </select>
<!-- </tr> -->