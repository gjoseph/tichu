import WebSocket from "ws";
import inquirer from "inquirer";
import { Cards } from "./cards";
import { GameMessage, JoinParam, Message } from "./actions";
import { GameOpts } from "./startup";

export class WSTichuClient {
    private console: Console;
    private _webSocket: WebSocket | undefined;

    constructor(readonly opts: GameOpts) {
        this.console = new Console(this.send, this.close);
    }

    connect(url: string) {
        this._webSocket = this.webSocketSetup(url, this.console);
        return this;
    }

    // After connection, only actions should be play or pass
    // and its maybe just a single question that lists cards and a special key/option to pass
    // .. or just, if no card selected > pass
    // and a confirmation
    //
    // https://www.npmjs.com/package/read has a timeout function which could also be interesting
    // https://www.npmjs.com/package/https-proxy-agent could be needed as well

    waitUntilDone(): Status {
        return "Done";
    }


    promptForCards = () => {
        inquirer
            .prompt([
                {
                    message:
                        "Pick cards, hit enter to play them. Just hit enter to pass.",
                    prefix: "<this is the prefix>",
                    suffix: "<this is the suffix>",
                    type: "checkbox",
                    name: "cards",
                    choices: Cards.sort(() => 0.5 - Math.random()).slice(0, 14),
                    pageSize: 20
                }
            ])
            .then((answers: any) => {
                console.log("answers:", answers);
                return "ok";
            });
    };

    private onConnect() {
        const joinParam = new JoinParam(this.opts.user, this.opts.team);
        const msg = new GameMessage(joinParam);
        this.send(msg);

        // on receive, set next prompt function

        // loop:
        // prompt for action, send we message
        this.promptForCards();
    }

    send = (msg: Message) => {
        this.ws().send(JSON.stringify(msg));
        this.console.prompt();
    };

    private receive(data: WebSocket.Data) {
        // cast will pbly fail
        this.console.print(
            Console.Types.Incoming,
            data as string,
            Console.Colors.Blue
        );
    }

    close = () => {
        this.ws().close(); //closeStatusCode, closeReason);
    };

    private ws(): WebSocket {
        if (!this._webSocket) {
            throw new Error("Websocket is not setup!");
        }
        return this._webSocket;
    }

    private webSocketSetup(url: string, wsConsole: Console): WebSocket {
        const noop = () => {};

        const ws = new WebSocket(url); // TODO wtf are subprotocols

        ws.on("open", () => {
            wsConsole.print(
                Console.Types.Control,
                "Connected (press CTRL+C to quit)",
                Console.Colors.Green
            );
            this.onConnect();
            // setup line-handlers on console object:
            /*
            wsConsole.on("line", data => {
                switch (data) {
                    case "/ping":
                        ws.ping(noop);
                        break;
                    case "/pong":
                        ws.pong(noop);
                        break;
                    case "/close": {
                        let closeStatusCode = 1000;
                        let closeReason = "";
                        if (toks.length >= 2) {
                            closeStatusCode = parseInt(toks[1]);
                        }
                        if (toks.length >= 3) {
                            closeReason = toks.slice(2).join(" ");
                        }
                        if (closeReason.length > 0) {
                            ws.close(closeStatusCode, closeReason);
                        } else {
                            ws.close(closeStatusCode);
                        }
                        break;
                    default:
                        ws.send(data);
                    }
                        wsConsole.prompt();
                }
            );*/
        });

        ws.on("close", (code, reason) => {
            wsConsole.print(
                Console.Types.Control,
                `Disconnected (code: ${code}, reason: "${reason}")`,
                Console.Colors.Green
            );
            wsConsole.clear();
            process.exit();
        });

        ws.on("error", err => {
            wsConsole.print(
                Console.Types.Error,
                err.message,
                Console.Colors.Yellow
            );
            process.exit(-1);
        });

        ws.on("message", (data: WebSocket.Data) => {
            this.receive(data);
        });

        ws.on("ping", () => {
            wsConsole.print(
                Console.Types.Incoming,
                "Received ping",
                Console.Colors.Blue
            );
        });

        ws.on("pong", () => {
            wsConsole.print(
                Console.Types.Incoming,
                "Received pong",
                Console.Colors.Blue
            );
        });

        return ws;
    }
}

// copied from wscat
class Console {
    // extends EventEmitter {
    // private stdin: NodeJS.ReadStream;
    private stdout: NodeJS.WriteStream;
    // private readlineInterface: Interface;
    private _resetInput: () => void;

    constructor(onLine: (line: string) => void, onClose: () => void) {
        // super();

        // this.stdin = process.stdin;
        this.stdout = process.stdout;

        // this.readlineInterface = readline.createInterface(
        //     this.stdin,
        //     this.stdout
        // );

        // this.readlineInterface.on("line", onLine).on("close", onClose);

        this._resetInput = () => {
            this.clear();
        };
    }

    static get Colors() {
        return {
            Red: "\u001b[31m",
            Green: "\u001b[32m",
            Yellow: "\u001b[33m",
            Blue: "\u001b[34m",
            Default: "\u001b[39m"
        };
    }

    static get Types() {
        return {
            Incoming: "< ",
            Control: "",
            Error: "error: "
        };
    }

    prompt() {
        // this.readlineInterface.prompt();
    }

    print(type: string, msg: string, color: string) {
        // if (tty.isatty(1)) {
        //     this.clear();
        //
        //     if (program.execute) color = type = "";
        //     else if (!program.color) color = "";

        this.stdout.write(color + type + msg + Console.Colors.Default + "\n");
        this.prompt();
        // } else if (type === Console.Types.Incoming) {
        //     this.stdout.write(msg + "\n");
        // } else {
        //     // is a control message and we're not in a tty... drop it.
        // }
    }

    clear() {
        // if (tty.isatty(1)) {
        this.stdout.write("\u001b[2K\u001b[3D");
        // }
    }

    // pause() {
    //     this.stdin.on("keypress", this._resetInput);
    // }
    //
    // resume() {
    //     this.stdin.removeListener("keypress", this._resetInput);
    // }
}

export type Status = "Done" | "Not done";
