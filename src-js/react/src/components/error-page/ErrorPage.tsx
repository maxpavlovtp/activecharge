import React from "react";
import Footer from "../footer/Footer";
import Header from "../header/Header";
import styles from './ErrorPage.module.css';

const ErrorPage = () => {
    return (
        <div className={styles.container}>
            <Header/>
            <h1 className={styles.title}>Sorry, some went wrong</h1>
            <Footer/>
        </div>
    )
}

export default ErrorPage;