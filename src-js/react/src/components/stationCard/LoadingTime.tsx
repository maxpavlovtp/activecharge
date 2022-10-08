import React from "react";
import styles from "./LoadingTime.module.css";

export function LoadingTime() {
  return <div className={styles.loading}></div>;
}

export function PayLinkLoading() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100%",
      }}
    >
      <div className={styles.loading}></div>
    </div>
  );
}
