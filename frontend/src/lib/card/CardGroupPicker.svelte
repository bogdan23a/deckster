<script lang='ts'>
	import CardGroupOption from './CardGroupOption.svelte';
    interface Props {
        cardGroups: any[];
        selectedResponses: number;
    }
    let { cardGroups, selectedResponses = $bindable() }: Props = $props();

    let stateGroups: any[] = $state(cardGroups);
    let selectedGroup: string = $state(stateGroups[0][0].response_group);

    $effect(() => {
        selectedResponses = selectedGroup !== "" ? 1 : 0;
    });

    let onClick = (card: any) => {
        if (selectedGroup === card.response_group) {
            selectedGroup = "";
            return;
        }
        selectedGroup = card.response_group;
    };
</script>

{#if cardGroups}
    <input type="hidden" hidden bind:value={selectedGroup} name="responseGroup">
    {#each stateGroups as group}
        <CardGroupOption cardGroup={group} selected={selectedGroup === group[0].response_group} {onClick}/>
    {/each}
{/if}