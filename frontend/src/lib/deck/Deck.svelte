<script lang='ts'>
    interface Props {
        deck: any;
    }

    let { deck }: Props = $props();
    
    let groups:any = deck["cards"]?.reduce((group:any, card:any) => {
        const { type } = card;
        if (type !== undefined) {
            if (!group.has(type['name'])) {
                return group.set(type['name'], 1);
            }
            group.set(type['name'], group.get(type['name']) + 1);
            return group;
        }
        return new Map;
    }, new Map);
</script>

<div class="pt-6">
    <a class="block card card-hover p-4" href="/deck/{deck['id']}">
        <header class="card-header">{deck['name']}</header>
        <section class="p-4"></section>
        <footer class="card-footer bottom-0">
            {#if groups.size !== 0}
                {#each [...groups] as [type, count]}
                    <p>{count} {type.toLowerCase()} 
                        {#if count == 1} 
                            card 
                        {:else} 
                            cards
                        {/if}
                    </p>
                {/each}
            {:else}
                <p>Empty</p>
            {/if}
        </footer>
    </a>
</div>

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
