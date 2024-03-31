import { Button, Image, Input, message, Modal } from "antd";
import { useEffect, useRef, useState } from "react";
import styles from './index.less';
import { atom, useAtom } from 'jotai'
import { PageContainer } from "@ant-design/pro-layout";

const countAtom = atom(0)
const listAtom = atom([])

function Sandbox(props) {


    const [count, setCount] = useAtom(countAtom)

    // 沙箱弹框a
    const [websocket, setWebsocket] = useState<WebSocket>();
    //   const [sandboxMessageList, setSandboxMessageList] = useState([]);
    const [sandboxMessageList, setSandboxMessageList] = useAtom(listAtom);
    const [messageInput, setMessageInput] = useState<string>();
    const chatContent = useRef<HTMLDivElement>();

    const setScrollTop = () =>{
        try{
            chatContent.current.scrollTop = chatContent.current?.scrollHeight;
        }catch(error){

        }
    }

    const createSandbox = () => {
        let wsAddr = '';
        //http://xxxxx:xxx/#/sandbox
        let url = window.location.href+"";
        console.log("当前游览器访问地址",url);

        if(url.startsWith("https")){
            wsAddr = wsAddr+"wss"
            url = url.replace("https","");
        }
        if(url.startsWith("http")){
            wsAddr = wsAddr+"ws"
            url = url.replace("http","");
        }
        url = url.replace("/#/sandbox","");
        if(url.endsWith(":8000")){
            url = url.replace("8000","8888");
        }
        wsAddr = wsAddr + url + '/ws/sandbox';

        const ws = new WebSocket(wsAddr);
        setSandboxMessageList([]);
        ws.onopen = () => {
            setSandboxMessageList([]);
            if (websocket) {
                websocket.close();
            }
            setWebsocket(ws);
            message.success("沙箱服务已连接")
        }
        ws.onmessage = (messageData) => {
            const newArray = sandboxMessageList;
            newArray.push(JSON.parse(messageData.data));
            setSandboxMessageList(newArray);
            setCount(newArray.length);
            setScrollTop();
            setTimeout(() => {
                    setScrollTop();
            }, 500);
        }
        ws.onerror = (error) => message.success("沙箱通信异常");
        ws.onclose = () => {
            setWebsocket(undefined);
        };
    }


    const changeMessageInput = (e: any) => {
        setMessageInput(e.target.value);
    }

    const sendSandboxMessage = () => {
        if (!messageInput || messageInput == '') {
            message.warn('请先输入消息');
            return;
        }
        websocket?.send(messageInput);
        setMessageInput(undefined);
    }

    useEffect(() => {
        if (!websocket) {
            createSandbox();
        }
    }, [props.open])


    return (
        <PageContainer>
            <div className={styles.chatRoom}>
                <div className={styles.chatRight} >共{count}条消息</div>
                <div className={styles.chatContent} ref={chatContent}>
                    {sandboxMessageList?.map((chatItem) => {
                        if (!chatItem?.isSelf) {
                            return (
                                <div className={styles.chatLeft}>
                                    <img src={'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAi9JREFUWEdjZBhgwDjA9jOQ7YD5K/d5MTL9XwbywP9/jFGJ4U7byPEM2Q5YuGZv+///DBUgSxkZGTriQ5wr6eqABWv2BTP8/78GbCkjY0hCiNNaujoAZNnCNXtiQHR8iMsSciwHu51cjSB981fvCwLRiaFO68g1B6sD0ho2c/1j4VnAwMDggM9gJsb/7C/efPz/7x/DT3zq/jMyHGD6xpK4eZbvN3R1WB2Q3LJvFiMDYyoxvnrx+gMxykB5ZfaWiUFpRDkgpXX/EYb/DNbEmEysA/4zMB7dOjHAZtQBZIeAurwAg7mOOMPr998Z7j39xHDzISTuYVFgrS/FEOqqBhZbs/c2w5HzT1HsIjoKrAKrdGSV9ZfzCojpIJvgaSXHEOSoBBY6fOE5w6KtN1EcMKHEgUFZRgAsdu/pR4b87v0oDvj5/f2Vzy9uV5/Y1L4JWQIlF1gEVDUwMjDUy6noXyHVARNLHBmUZPjBZt9/+pEhD4sDvry6o/OfgaHxxIa2BpgjiHYAoSiwNZRmCHVBRMGhc08wQoAiB+DKksRmQ1AUDBkHpMqp6L9DTwPUCYG7PxkY/pcf39C2F2sagAmGl6y8TAsH7J6VpEt2OUBpCBBdDoAsGq0LRkMguWX/PkYGBkdqtgcYGBj3b5kY4ERULkhu3u/ByMiwnZoOYGJi8tzU77+DKAeAFCW2HJRl+f9Pn5Ajnr/6SLhhy8h0ccskv0fYzCKsmZALKJQfdcCAhwAARABSMB5w6tgAAAAASUVORK5CYII='} alt="" />
                                    <div className={styles.info}>
                                        <div className={styles.name}>{'JLC-BOT'}</div>
                                        <div className={styles.textCon}>
                                            {
                                                chatItem?.type == 'image' && <div className={styles.text} ><img style={{ "objectFit": "contain","height": "50%", "width": "25%", "borderRadius": "0px" }} src={chatItem?.message} /></div>
                                            }
                                            {
                                                chatItem?.type == 'text' && <div className={styles.text} >{chatItem?.message}</div>
                                            }
                                            {
                                                chatItem?.type == 'video' &&
                                                <div className={styles.text} >
                                                    <video style={{ "objectFit": "contain", "width": "20%" }} src={chatItem?.message} type="video/mp4" controls autoplay>
                                                    </video>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                </div>
                            )
                        } else {
                            return (
                                <div className={styles.chatRight}>
                                    <img src={'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAzBJREFUWEfFl8trlFcYxn/PF1xYjGiwEWO1iFcEidoiiqSCFBSxUeii1G27EP8Jwf/BuHHjTgQXCmaheKmCrSItLaWBjJdicLQlovVSqNXvkfN9mcnMZGbON0MwZzEDM+/leZ/3cs4rps6ikdJwAv2I5aSsqPw+q98JE5hHKfz9/Mja88G2wkfm3AzPqrOIsVScDyA0F84r2AIILR4ZPyzr8w8ZfcWX5TvqOz5+DDTQMQCxwKl7JT3uWLeq4LL6jpdORg3IO0Dz/I4fSdyDtEewZNoOD4ArdXaUrIN0yPYvQj+38lEEwBBiXWYg5bbxpBLtazTY6Mgp80hYkZiVFte6BbANsSlTNq/AFyUNGlY3M+jUow0pOWj7Vrs0tWRAMGiRF6dJgYuIZcBg82j8P+gGztIBShba6ZLEfmjpbWcMiA3AzpocXwdKKFDvAKLpqWUg1IjFAaU6ZbkDAOITYE/Fg8wdw685Ed4qaUtz96EbfBPzvKobQMBkuyKfkYJ6J3qMPdpgYDdiVawIo501JTCzBupobgogZwKtRSyALPKxau6Lem4JAOoizNur5x6k/8Rs214WCjUheWH4F1yO6TRhIKv8GZUu81eKy5Lu1+Y5r3g2GNbXDafc8wOnHuumDb/J6W3ZPCG6SdtPpWQ9+KNIpOM2fwieNsq1m4TfImKGYwxP/y+/wbqGmahVajuKjb6U/GlxL1HJEibMlOqJ3gWCzRafRU1XBOzXWL+5h/9kDYIXV3XNa+B0RwCmhJcj9hYCYcaAm0E2dEXtxRWGksy5bgAEndD3X0RB1AAAbSS7yvNj84PgbrcAQDoEnt8eRHV4LUTsBzL5MM7DWO+kC2b4sRgWfNwWgPkduIU0BM7eEUJPbF9ophctwjol6WvwonYAQo4tegkTtcq9zoBfzgIAvitCv9B+y0unEn8Z9GcrvcIMyMx3wqEiAKZTpYfYl9oyVuhRmm8w/RZfRbsAzsp6mTrtL/JiVt+J0tEiq5jQGsu74gD0DHMV/CwqmzChvpHS95jtMeHsqu3wRBkQP839ahaCmov9sLqcVlgNIIQHZAa6WtUKpcdli7JRubKevwfzvno4iafbyQAAAABJRU5ErkJggg=='} alt="" />
                                    <div className={styles.info}>
                                        <div className={styles.name}>{'我'}</div>
                                        <div className={styles.textCon}>
                                            <div className={styles.text}>{chatItem?.message}</div>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    })}
                </div>
                <div className={styles.inputArea}>
                    <Input placeholder="请输入内容" size="large" onKeyDown={(event) => {
                        if (event.key == 'Enter' && messageInput && messageInput != '') {
                            sendSandboxMessage();
                        }
                    }} value={messageInput} onChange={(e: any) => changeMessageInput(e)} />
                    <Button size="large" type="primary" onClick={sendSandboxMessage}>发送</Button>
                </div>
            </div>
        </PageContainer>

    );
}

export default Sandbox;