<script lang='ts'>
	import { goto } from '$app/navigation';
    import { ListBoxItem, type PopupSettings } from '@skeletonlabs/skeleton';
    import { popup } from '@skeletonlabs/skeleton';
	let valueSingle = $state('books');
    let card_types = new Map([
        ["black", 1],
        ["white", 1]]);
    let backendUri = `${import.meta.env.VITE_DECKMASTER_PROTOCOL}://${import.meta.env.VITE_DECKMASTER_URI}`
    interface Props {
        deck: any;
    }

    let { deck }: Props = $props();
    const deleteDeck = (e: PointerEvent) => {
        console.log(e.target['id']);
        fetch(`${backendUri}/deck/${e.target['id']}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(() => goto("/decks"))
    } 
    const popupClick: PopupSettings = {
	    event: 'click',
	    target: 'popupClick',
	    placement: 'top',
        state: (e: Record<string, boolean>) => {
            if (e.state) {
                document.getElementsByClassName("deleteButton")[0].id = deck['id']
            }
    }};
</script>

<ListBoxItem bind:group={valueSingle} name="medium" value="books">
    <button class="block card card-hover p-4" use:popup={popupClick}>
        <header class="card-header">{deck['name']}</header>
        <section class="p-4"></section>
        <footer class="card-footer bottom-0">
            {#each [...card_types] as [type, count]}
                <p>{count} {type} 
                    {#if count == 1} 
                        card 
                    {:else} 
                        cards
                    {/if}
                </p>
            {/each}
        </footer>
    </button>

    <div class="p-4 variant-filled-surface" data-popup="popupClick">
        <div class="grid grid-cols-1 gap-2">
            <button id="wont-close" class="btn variant-filled-warning">Edit</button>
            <button id={deck['id']} class="btn variant-filled-error deleteButton" onclick={deleteDeck}>Delete</button>
        </div>
        <div class="arrow bg-surface-100-800-token"></div>
    </div>
</ListBoxItem>

<style lang="postcss">
	.card {
		display: flex;
		flex-direction: column;
		flex-wrap: wrap;
        width: 250px;
        height: 350px;
        border-color: rgb(255, 255, 255);
        border-style: solid;
        border-width: 2px;
        border-radius: 13px;
        background-color: rgb(0, 0, 0) !important;
        color: rgb(255, 255, 255);
        font-family:  Neue LT,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,sans-serif;
        font-weight: 400;
        font-size: 20px;
        line-height: 26px;
	}
</style>
