import React from "react";
import styles from "./ErrorPage.module.css";

const ErrorPage = ({
  errorHeader,
  errorBody,
}: {
  errorHeader: string;
  errorBody: string;
}) => {
  return (
    <div className={styles.container}>
      <p className={styles.title}>{errorHeader}</p>
      <p className={styles.body}>{errorBody}</p>
    </div>
  );
};

export default ErrorPage;
