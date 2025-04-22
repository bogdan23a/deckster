<script lang="ts">
	import CardGroupPicker from "$lib/card/CardGroupPicker.svelte";
	import CardList from "$lib/card/CardList.svelte";
	import CardPicker from "$lib/card/CardPicker.svelte";
	import PlayersCard from "$lib/card/PlayersCard.svelte";
	import ScoreCard from "$lib/card/ScoreCard.svelte";
	import DeckList from "$lib/deck/DeckList.svelte";

    let { task } = $props();
    let { details, events } = task;
    let stateDetails = $state(details);
    let prompts: number = $state(stateDetails.map((detail: any) => detail.input).filter((input: any) => input.input_type === "PROMPT").map((prompts: any) => prompts.values[0].prompts)[0]);
    let selectedResponses = $state(events[0] === 'PICK_WINNER' ? 1 : prompts);

    const messages = {
        "DECK": "Choose Your Deck",
        "PLAYERS": "Table",
        "PROMPT": "Prompt",
        "RESPONSES": "Choose your response/s",
        "WINNER": "Choose The Winner",
        "HOST_SETUP": "The host is setting up the game",
        "OTHER_PLAYERS_TURN": "Waiting for other players to finish their turn",
        "SCORE": "",
        "SHARE_GAME": "Share this game with your friends"
    }
</script>

<div class="grid grid-cols-8 m-4">
    {#each details as detail}
        <div class="col-span-8">
            {messages[detail.label]}
        </div>
        {#if detail.input.input_type === "LINK"}
            <button class="btn hover:border-t-cyan-200" onclick={() => navigator.share()}>share</button>
        {/if}
        {#if detail.input.input_type === "LIST"}
            <PlayersCard players={detail.input.values} />
        {/if}
        {#if detail.input.input_type == "PROMPT"}
            <CardList cards={detail.input.values}/>
        {/if}
        {#if detail.input.input_type === "CARD_PICKER"}
            {#if detail.label === "WINNER"}
                <CardGroupPicker cardGroups={detail.input.values} bind:selectedResponses/>
            {:else}
                <CardPicker cards={detail.input.values} bind:selectedResponses requiredResponses={prompts}/>
            {/if}
        {/if}
        {#if detail.input.input_type === "DECK_PICKER"}
            <DeckList decks={detail.input.values}/>
        {/if}
        {#if detail.input.input_type === "SCORE"}
            <ScoreCard players={detail.input.values} />
        {/if}
        
    {/each}
</div>
{#each events as event}
    <button class="btn btn-primary bg-white border-black border-2 rounded-xl p-4 hover:boder-4 hover:font-bold disabled:border-2 disabled:font-normal disabled:bg-gray-200 disabled:text-gray-500 disabled:border-gray-500 m-4" disabled={event === "PICK_WINNER" ? selectedResponses !== 1 : selectedResponses !== prompts} value={event} type="submit" formaction="?/{event}">{event.replace("_", " ")}</button>
{/each}