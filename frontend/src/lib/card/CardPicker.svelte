<script lang='ts'>
	import CardOption from './CardOption.svelte';
    interface Props {
        cards: any;
        selectedResponses: any;
        requiredResponses: number;
    }
    let { cards, requiredResponses, selectedResponses = $bindable() }: Props = $props();

    let stateCards: any[] = $state(cards);
    let selectedCards: any[] = $state(stateCards.slice(0, requiredResponses));
    let selectedIds: string[] = $state([]);

    $effect(() => {
        selectedIds = selectedCards.map((card: any) => card.id);
    });

    let onClick = (card: any) => {
        if (selectedCards.includes(card)) {
            const index = selectedCards.indexOf(card);
            selectedCards = [
                ...selectedCards.slice(0, index),
                ...selectedCards.slice(index + 1, selectedCards.length)
            ]
            selectedResponses = selectedCards.length;
            selectedIds = selectedCards.map((card: any) => card.id);
            return;
        }
        if (selectedCards.length >= requiredResponses) {
            selectedCards.pop();
        }
        selectedCards.push(card);
        selectedResponses = selectedCards.length;
        selectedIds = selectedCards.map((card: any) => card.id)
    };

    let getSelectionIndex = (card: any) => {
        return selectedCards.indexOf(card) + 1;
    }
</script>

{#if cards}
    <input type="hidden" hidden bind:value={selectedIds} name="cardIds[]">
    {#each stateCards as card}
        <CardOption {card} selected={selectedCards.includes(card)} {onClick} selectionIndex={getSelectionIndex(card)}/>
    {/each}
{/if}