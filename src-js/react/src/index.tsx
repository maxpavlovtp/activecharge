import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";

import './fonts/OpenSans-Light.ttf';
import './fonts/OpenSans-Medium.ttf';
import './fonts/OpenSans-Regular.ttf';
import './fonts/OpenSans-SemiBold.ttf';
import './fonts/OpenSans-Bold.ttf';
import './fonts/OpenSans-ExtraBold.ttf';

import "./i18n";
import { Provider } from "react-redux";
import { setupStore } from "./store/store";

const store = setupStore();

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
