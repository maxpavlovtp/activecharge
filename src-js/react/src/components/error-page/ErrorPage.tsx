import React from "react";
import styles from "./ErrorPage.module.css";

const ErrorPage = ({
  numError,
  errorHeader,
  errorBody,
}: {
  numError: string;
  errorHeader: string;
  errorBody: string;
}) => {
  return (
    <div className={styles.container}>
      <p className={styles.numError}>{numError}</p>
      <p className={styles.title}>{errorHeader}</p>
      <p className={styles.body}>{errorBody}</p>
    </div>
  );
};

export default ErrorPage;
