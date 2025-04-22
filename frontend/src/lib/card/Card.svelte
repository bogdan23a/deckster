<script lang='ts'>
    interface Props {
        card: any;
    }

    let { card }: Props = $props();
</script>

<div class="pt-6">
    <div class="block card card-hover {card.type.name}">
        <header class="card-header"></header>
        <section class="p-4">{card.content}</section>
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
	.Prompt {
        border-color: rgb(255, 255, 255);
        background-color: rgb(0, 0, 0) !important;
        color: rgb(255, 255, 255);
	}

    .Response {
        border-color: rgb(0, 0, 0);
        background-color: rgb(255, 255, 255) !important;
        color: rgb(0, 0, 0);
    }
</style>
