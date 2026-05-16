"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Navigation(){
    const path = usePathname();
    return (
        <nav>
            <ul>
                <li>
                    <Link href="/">Home</Link> {path === "/" ? "fire" : ""}
                </li>
                <li>
                    <Link href="/about-us">About us</Link>{path === "/about-us" ? "fire" : ""}
                </li>
            </ul>
        </nav>
    )
}