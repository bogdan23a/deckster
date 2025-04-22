<script lang='ts'>
    interface Props {
        players: any;
    }

    let { players }: Props = $props();
</script>

<div class="pt-6">
    <div class="block card card-hover Response">
        <header class="card-header p-2"></header>
        <section class="p-4">
            {#each players as player}
                <p>{player.email}</p>
            {/each}
        </section>
        <footer class="card-footer bottom-0"></footer>
    </div>
</div>

<style lang="postcss">
    .card {
        display: flex;
		flex-direction: column;
		flex-wrap: wrap;
        width: 200px;
        height: 280px;
        border-style: solid;
        border-width: 2px;
        border-radius: 13px;
        font-family:  Neue LT,-apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,sans-serif;
        font-weight: 400;
        font-size: 20px;
        line-height: 26px;
    }

    .card-header {
        font-size: 20px;
    }

    .Response {
        border-color: rgb(0, 0, 0);
        background-color: rgb(255, 255, 255) !important;
        color: rgb(0, 0, 0);
        font-size: 10px;
    }
</style>
