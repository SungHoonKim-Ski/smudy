--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: learn_report; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.learn_report (
    learn_report_id integer NOT NULL,
    song_id character varying(30) NOT NULL,
    problem_type text NOT NULL,
    learn_report_date date DEFAULT now() NOT NULL,
    learn_report_score integer NOT NULL,
    learn_report_total integer NOT NULL,
    user_internal_id uuid NOT NULL,
    CONSTRAINT learn_report_problem_type_check CHECK ((problem_type = ANY (ARRAY['FILL'::text, 'PICK'::text, 'EXPRESS'::text, 'PRONOUNCE'::text])))
);


ALTER TABLE public.learn_report OWNER TO smudy;

--
-- Name: learn_report_express; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.learn_report_express (
    learn_report_id integer NOT NULL,
    problem_box_id bigint NOT NULL,
    learn_report_express_suggest text[] NOT NULL,
    learn_report_express_user_en text[] NOT NULL,
    learn_report_express_user_ko text[] NOT NULL,
    learn_report_express_score integer[] NOT NULL
);


ALTER TABLE public.learn_report_express OWNER TO smudy;

--
-- Name: learn_report_express_learn_report_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.learn_report_express_learn_report_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.learn_report_express_learn_report_id_seq OWNER TO smudy;

--
-- Name: learn_report_express_learn_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.learn_report_express_learn_report_id_seq OWNED BY public.learn_report_express.learn_report_id;


--
-- Name: learn_report_fill; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.learn_report_fill (
    learn_report_id integer NOT NULL,
    song_id character(30) NOT NULL,
    learn_report_fill_user text[] NOT NULL,
    learn_report_fill_is_correct boolean[] NOT NULL
);


ALTER TABLE public.learn_report_fill OWNER TO smudy;

--
-- Name: learn_report_fill_learn_report_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.learn_report_fill_learn_report_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.learn_report_fill_learn_report_id_seq OWNER TO smudy;

--
-- Name: learn_report_fill_learn_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.learn_report_fill_learn_report_id_seq OWNED BY public.learn_report_fill.learn_report_id;


--
-- Name: learn_report_learn_report_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.learn_report_learn_report_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.learn_report_learn_report_id_seq OWNER TO smudy;

--
-- Name: learn_report_learn_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.learn_report_learn_report_id_seq OWNED BY public.learn_report.learn_report_id;


--
-- Name: learn_report_pick; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.learn_report_pick (
    learn_report_id integer NOT NULL,
    problem_box_id bigint NOT NULL,
    learn_report_pick_user text[] NOT NULL
);


ALTER TABLE public.learn_report_pick OWNER TO smudy;

--
-- Name: learn_report_pick_learn_report_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.learn_report_pick_learn_report_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.learn_report_pick_learn_report_id_seq OWNER TO smudy;

--
-- Name: learn_report_pick_learn_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.learn_report_pick_learn_report_id_seq OWNED BY public.learn_report_pick.learn_report_id;


--
-- Name: learn_report_pronounce; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.learn_report_pronounce (
    learn_report_id integer NOT NULL,
    learn_report_pronounce_user_en text NOT NULL,
    lyric_sentence_en text NOT NULL,
    lyric_sentence_ko text NOT NULL,
    lyric_ai_analyze json NOT NULL
);


ALTER TABLE public.learn_report_pronounce OWNER TO smudy;

--
-- Name: learn_report_pronounce_learn_report_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.learn_report_pronounce_learn_report_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.learn_report_pronounce_learn_report_id_seq OWNER TO smudy;

--
-- Name: learn_report_pronounce_learn_report_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.learn_report_pronounce_learn_report_id_seq OWNED BY public.learn_report_pronounce.learn_report_id;


--
-- Name: streak; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.streak (
    strict_id integer NOT NULL,
    song_jacket character varying(200) NOT NULL,
    streak_date date NOT NULL,
    user_internal_id uuid NOT NULL
);


ALTER TABLE public.streak OWNER TO smudy;

--
-- Name: streak_strict_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.streak_strict_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.streak_strict_id_seq OWNER TO smudy;

--
-- Name: streak_strict_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.streak_strict_id_seq OWNED BY public.streak.strict_id;


--
-- Name: studylist; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.studylist (
    studylist_id integer NOT NULL,
    song_id character varying(30) NOT NULL,
    user_internal_id uuid NOT NULL
);


ALTER TABLE public.studylist OWNER TO smudy;

--
-- Name: studylist_studylist_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.studylist_studylist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.studylist_studylist_id_seq OWNER TO smudy;

--
-- Name: studylist_studylist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.studylist_studylist_id_seq OWNED BY public.studylist.studylist_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_internal_id uuid NOT NULL,
    user_sns_id character varying(50) NOT NULL,
    user_name character varying(50) NOT NULL,
    user_image character varying(200) NOT NULL,
    user_exp integer DEFAULT 0 NOT NULL,
    user_created_at date DEFAULT CURRENT_DATE NOT NULL,
    user_deleted_at date,
    user_is_activate boolean DEFAULT true NOT NULL,
    user_role character varying(50) DEFAULT 'USER'::character varying NOT NULL
);


ALTER TABLE public.users OWNER TO smudy;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: smudy
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO smudy;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: smudy
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- Name: wrong; Type: TABLE; Schema: public; Owner: smudy
--

CREATE TABLE public.wrong (
    wrong_id bigint NOT NULL,
    lyric_sentence_en text NOT NULL,
    lyric_sentence_ko text NOT NULL,
    user_internal_id uuid NOT NULL
);


ALTER TABLE public.wrong OWNER TO smudy;

--
-- Name: learn_report learn_report_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report ALTER COLUMN learn_report_id SET DEFAULT nextval('public.learn_report_learn_report_id_seq'::regclass);


--
-- Name: learn_report_express learn_report_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_express ALTER COLUMN learn_report_id SET DEFAULT nextval('public.learn_report_express_learn_report_id_seq'::regclass);


--
-- Name: learn_report_fill learn_report_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_fill ALTER COLUMN learn_report_id SET DEFAULT nextval('public.learn_report_fill_learn_report_id_seq'::regclass);


--
-- Name: learn_report_pick learn_report_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pick ALTER COLUMN learn_report_id SET DEFAULT nextval('public.learn_report_pick_learn_report_id_seq'::regclass);


--
-- Name: learn_report_pronounce learn_report_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pronounce ALTER COLUMN learn_report_id SET DEFAULT nextval('public.learn_report_pronounce_learn_report_id_seq'::regclass);


--
-- Name: streak strict_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.streak ALTER COLUMN strict_id SET DEFAULT nextval('public.streak_strict_id_seq'::regclass);


--
-- Name: studylist studylist_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.studylist ALTER COLUMN studylist_id SET DEFAULT nextval('public.studylist_studylist_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- Data for Name: learn_report; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.learn_report (learn_report_id, song_id, problem_type, learn_report_date, learn_report_score, learn_report_total, user_internal_id) FROM stdin;
1	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-02	52	48	74cb4a7c-1751-4ede-829a-de5046ef4688
2	0BUuuEvNa5T4lMaewyiudB	PICK	2024-05-02	3	5	74cb4a7c-1751-4ede-829a-de5046ef4688
3	0BUuuEvNa5T4lMaewyiudB	EXPRESS	2024-05-02	0	100	74cb4a7c-1751-4ede-829a-de5046ef4688
4	0BUuuEvNa5T4lMaewyiudB	PRONOUNCE	2024-05-02	70	100	74cb4a7c-1751-4ede-829a-de5046ef4688
8	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-05	52	52	74cb4a7c-1751-4ede-829a-de5046ef4688
9	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-05	42	52	74cb4a7c-1751-4ede-829a-de5046ef4688
10	1EzrEOXmMH3G43AXT1y7pA	PICK	2024-05-05	0	5	74cb4a7c-1751-4ede-829a-de5046ef4688
11	1EzrEOXmMH3G43AXT1y7pA	PICK	2024-05-05	0	5	74cb4a7c-1751-4ede-829a-de5046ef4688
12	1EzrEOXmMH3G43AXT1y7pA	PICK	2024-05-05	5	5	74cb4a7c-1751-4ede-829a-de5046ef4688
13	1EzrEOXmMH3G43AXT1y7pA	PICK	2024-05-05	3	5	74cb4a7c-1751-4ede-829a-de5046ef4688
14	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-02	52	48	0032c8bd-47c0-488a-b4aa-066c8e3e249e
15	0BUuuEvNa5T4lMaewyiudB	PICK	2024-05-02	3	5	0032c8bd-47c0-488a-b4aa-066c8e3e249e
16	0BUuuEvNa5T4lMaewyiudB	EXPRESS	2024-05-02	0	100	0032c8bd-47c0-488a-b4aa-066c8e3e249e
17	0BUuuEvNa5T4lMaewyiudB	PRONOUNCE	2024-05-02	70	100	0032c8bd-47c0-488a-b4aa-066c8e3e249e
18	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-05	52	52	0032c8bd-47c0-488a-b4aa-066c8e3e249e
19	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-12	12	52	acb388f9-8522-4376-9a4e-fa3c0ab2301d
20	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-13	11	52	acb388f9-8522-4376-9a4e-fa3c0ab2301d
21	1EzrEOXmMH3G43AXT1y7pA	FILL	2024-05-13	12	52	acb388f9-8522-4376-9a4e-fa3c0ab2301d
22	asdkasdaslkdas	PRONOUNCE	2024-05-14	-1	-1	0032c8bd-47c0-488a-b4aa-066c8e3e249e
23	asdkasdaslkdas	PRONOUNCE	2024-05-14	-1	-1	0032c8bd-47c0-488a-b4aa-066c8e3e249e
\.


--
-- Data for Name: learn_report_express; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.learn_report_express (learn_report_id, problem_box_id, learn_report_express_suggest, learn_report_express_user_en, learn_report_express_user_ko, learn_report_express_score) FROM stdin;
3	3281	{"240 thousand miles from the moon","'Cause here it comes it's a light, a beautiful light","Home, home, home","know Just","He told me, son sometimes it may dark seem"}	{"240 thousand miles from the moon","'Cause here it comes it's a light, a beautiful light","Home, home, home","know Just","He told me, son22222222222222222 sometimes it may3333333 dark seem"}	{"240 thousand miles from the moon","'Cause here it comes it's a light, a beautiful light","Home, home, home","know Jus444444t","He told me, son so6666666666metimes it may dark seem"}	{95,85,75,100,80}
\.


--
-- Data for Name: learn_report_fill; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.learn_report_fill (learn_report_id, song_id, learn_report_fill_user, learn_report_fill_is_correct) FROM stdin;
1	1EzrEOXmMH3G43AXT1y7pA        	{well,melted,cracks,try,bestest,intervention,reckon,learn,hesitate,wait,"","",well,plans,heart,listen,family,godforsaken,hesitate,wait,complicate,fate,"","",want,scooch,nibble,"",spent,backward,breath,laughed,saying,vanities,"",virtue,hesitate,wait,open,plans,heart,please,complicate,long,fate,"","","","","",believe,believe}	{t,t,t,f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f,t,t,t,t,t,t,t,t,t,t,t,t,t,f,t,t,t,t,t,t,t,t}
8	1EzrEOXmMH3G43AXT1y7pA        	{well,melted,cracks,trying,bestest,intervention,reckon,learn,hesitate,wait,"","",well,plans,heart,listen,family,godforsaken,hesitate,wait,complicate,fate,"","",want,scooch,nibble,"",spending,backwards,breath,laughed,saying,vanities,"",virtue,hesitate,wait,open,plans,heart,please,complicate,short,fate,"","","","","",believe,believe}	{t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t}
9	1EzrEOXmMH3G43AXT1y7pA        	{well3,melted,cracks,trying,bestest,inte3rvention,reckon,learn,hesitate,w3ait,"","",well,plans,hea3rt,listen,family,godforsaken,hesi3tate,wait,complicate,fate,"","",w3ant,scooch,nibble,"",spending,backwards,bre3ath,laughed,saying,vanities,"",vi3rtue,hesitate,wait,open,plans,hea3rt,please,complicate,sh3ort,fate,"","","","","",believe,believe}	{f,t,t,t,t,f,t,t,t,f,t,t,t,t,f,t,t,t,f,t,t,t,t,t,f,t,t,t,t,t,f,t,t,t,t,f,t,t,t,t,f,t,t,f,t,t,t,t,t,t,t,t}
14	1EzrEOXmMH3G43AXT1y7pA        	{well3,melted,cracks,trying,bestest,inte3rvention,reckon,learn,hesitate,w3ait,"","",well,plans,hea3rt,listen,family,godforsaken,hesi3tate,wait,complicate,fate,"","",w3ant,scooch,nibble,"",spending,backwards,bre3ath,laughed,saying,vanities,"",vi3rtue,hesitate,wait,open,plans,hea3rt,please,complicate,sh3ort,fate,"","","","","",believe,believe}	{f,t,t,t,t,f,t,t,t,f,t,t,t,t,f,t,t,t,f,t,t,t,t,t,f,t,t,t,t,t,f,t,t,t,t,f,t,t,t,t,f,t,t,f,t,t,t,t,t,t,t,t}
18	1EzrEOXmMH3G43AXT1y7pA        	{well3,melted,cracks,trying,bestest,inte3rvention,reckon,learn,hesitate,w3ait,"","",well,plans,hea3rt,listen,family,godforsaken,hesi3tate,wait,complicate,fate,"","",w3ant,scooch,nibble,"",spending,backwards,bre3ath,laughed,saying,vanities,"",vi3rtue,hesitate,wait,open,plans,hea3rt,please,complicate,sh3ort,fate,"","","","","",believe,believe}	{f,t,t,t,t,f,t,t,t,f,t,t,t,t,f,t,t,t,f,t,t,t,t,t,f,t,t,t,t,t,f,t,t,t,t,f,t,t,t,t,f,t,t,f,t,t,t,t,t,t,t,t}
19	1EzrEOXmMH3G43AXT1y7pA        	{well,"      "," dd   ","      ","       ","            ","      ","     ","        ","    ","","","    ","     ","     ","      ","      ","           ","        ","    ","          ","    ","","","    ","      ","      ","","        ","         ","      ","       ","      ","        ","","      ","        ","    ","    ","     ","     ","      ","          ","     ","    ","","","","","","       ","       "}	{t,f,f,f,f,f,f,f,f,f,t,t,f,f,f,f,f,f,f,f,f,f,t,t,f,f,f,t,f,f,f,f,f,f,t,f,f,f,f,f,f,f,f,f,f,t,t,t,t,t,f,f}
20	1EzrEOXmMH3G43AXT1y7pA        	{Well,"      ","      ","      ","       ","            ","      ","     ","        ","    ","","","    ","     ","     ","      ","      ","           ","        ","    ","          ","    ","","","    ","      ","      ","","        ","         ","      ","       ","      ","        ","","      ","        ","    ","    ","     ","     ","      ","          ","     ","    ","","","","","","       ","       "}	{f,f,f,f,f,f,f,f,f,f,t,t,f,f,f,f,f,f,f,f,f,f,t,t,f,f,f,t,f,f,f,f,f,f,t,f,f,f,f,f,f,f,f,f,f,t,t,t,t,t,f,f}
21	1EzrEOXmMH3G43AXT1y7pA        	{well,"      ","      ","      ","       ","            ","      ","     ","        ","    ","","","    ","     ","     ","      ","      ","           ","        ","    ","          ","    ","","","    ","      ","      ","","        ","         ","      ","       ","      ","        ","","      ","        ","    ","    ","     ","     ","      ","          ","     ","    ","","","","","","       ","       "}	{t,f,f,f,f,f,f,f,f,f,t,t,f,f,f,f,f,f,f,f,f,f,t,t,f,f,f,t,f,f,f,f,f,f,t,f,f,f,f,f,f,f,f,f,f,t,t,t,t,t,f,f}
\.


--
-- Data for Name: learn_report_pick; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.learn_report_pick (learn_report_id, problem_box_id, learn_report_pick_user) FROM stdin;
2	3281	{"240 thousand miles from the moon","'Cause here it comes it's a light, a beautiful light","Home, home, home","know Just","He told me, son sometimes it may dark seem"}
10	3272	{"It cannot wait, I'm sure","Scooch on over closer, dear","Now I'm trying to get back","This, oh, this, oh, this is our fate","Before the cool done run out, I'll be givin' it my bestest"}
11	3272	{"It cannot wait, I'm sure","Scooch on over closer, dear","Now I'm trying to get back","This, oh, this, oh, this is our fate","Before the cool done run out, I'll be givin' it my bestest"}
12	3272	{"It cannot wait, I'm sure","Scooch on over closer, dear","Now I'm trying to get back","This, oh, this, oh, this is our fate","Before the cool done run out, I'll be givin' it my bestest"}
13	3272	{"It cannot wai111t, I'm sure","Scooch on over closer, dear","Now I'm trying to get back","This, oh, this, oh, this is our fate","Before the co111ol done run out, I'll be givin' it my bestest"}
\.


--
-- Data for Name: learn_report_pronounce; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.learn_report_pronounce (learn_report_id, learn_report_pronounce_user_en, lyric_sentence_en, lyric_sentence_ko, lyric_ai_analyze) FROM stdin;
4	Before the coll done ran about, I will been give' it my best"	Before the lyyyyyyric coll done ran about, I will been give' it my best"	이것만 하면	{\r\n    "ref_timestamps": [\r\n        {\r\n            "word": "I",\r\n            "start_time": 0.0,\r\n            "end_time": 0.4\r\n        },\r\n        {\r\n            "word": "used",\r\n            "start_time": 0.4,\r\n            "end_time": 0.6\r\n        },\r\n        {\r\n            "word": "to",\r\n            "start_time": 0.6,\r\n            "end_time": 0.7\r\n        },\r\n        {\r\n            "word": "rule",\r\n            "start_time": 0.7,\r\n            "end_time": 0.9\r\n        },\r\n        {\r\n            "word": "the",\r\n            "start_time": 0.9,\r\n            "end_time": 1.0\r\n        },\r\n        {\r\n            "word": "world",\r\n            "start_time": 1.0,\r\n            "end_time": 1.1\r\n        }\r\n    ],\r\n    "test_timestamps": [\r\n        {\r\n            "word": "I",\r\n            "start_time": 0.2,\r\n            "end_time": 2.7\r\n        },\r\n        {\r\n            "word": "used",\r\n            "start_time": 2.7,\r\n            "end_time": 2.9\r\n        },\r\n        {\r\n            "word": "to",\r\n            "start_time": 2.9,\r\n            "end_time": 3.6\r\n        },\r\n        {\r\n            "word": "rude",\r\n            "start_time": 3.6,\r\n            "end_time": 4.9\r\n        },\r\n        {\r\n            "word": "to",\r\n            "start_time": 4.9,\r\n            "end_time": 5.5\r\n        },\r\n        {\r\n            "word": "world",\r\n            "start_time": 5.5,\r\n            "end_time": 6.5\r\n        }\r\n    ],\r\n    "ref_pitch_data": {\r\n        "times": [\r\n            1.0,\r\n            1.02,\r\n            1.04,\r\n            1.06,\r\n            1.08,\r\n            1.1\r\n        ],\r\n        "values": [\r\n            165.56708351799784,\r\n            168.3686070363352,\r\n            170.92842657148458,\r\n            173.31039491145694,\r\n            174.6309464021384,\r\n            174.63175212093432\r\n        ]\r\n    },\r\n    "test_pitch_data": {\r\n        "times": [\r\n            5.5,\r\n            5.510416666666667,\r\n            5.520833333333333,\r\n            5.53125,\r\n            5.541666666666667,\r\n            5.552083333333333,\r\n            5.5625,\r\n            5.572916666666667,\r\n            5.583333333333333,\r\n            5.59375,\r\n            5.604166666666667,\r\n            5.614583333333333,\r\n            5.625,\r\n            5.635416666666667,\r\n            5.645833333333333,\r\n            5.65625,\r\n            5.666666666666667,\r\n            5.677083333333333,\r\n            5.6875,\r\n            5.697916666666667,\r\n            5.708333333333333,\r\n            5.71875,\r\n            5.729166666666667,\r\n            5.739583333333333,\r\n            5.75,\r\n            5.760416666666667,\r\n            5.770833333333333,\r\n            5.78125,\r\n            5.791666666666667,\r\n            5.802083333333333,\r\n            5.8125,\r\n            5.822916666666667,\r\n            5.833333333333333,\r\n            5.84375,\r\n            5.854166666666667,\r\n            5.864583333333333,\r\n            5.875,\r\n            5.885416666666667,\r\n            5.895833333333333,\r\n            5.90625,\r\n            5.916666666666667,\r\n            5.927083333333333,\r\n            5.9375,\r\n            5.947916666666667,\r\n            5.958333333333333,\r\n            5.96875,\r\n            5.979166666666667,\r\n            5.989583333333333,\r\n            6.0,\r\n            6.010416666666667,\r\n            6.020833333333333,\r\n            6.03125,\r\n            6.041666666666667,\r\n            6.052083333333333,\r\n            6.0625,\r\n            6.072916666666667,\r\n            6.083333333333333,\r\n            6.09375,\r\n            6.104166666666667,\r\n            6.114583333333333,\r\n            6.125,\r\n            6.135416666666667,\r\n            6.145833333333333,\r\n            6.15625,\r\n            6.166666666666667,\r\n            6.177083333333333,\r\n            6.1875,\r\n            6.197916666666667,\r\n            6.208333333333333,\r\n            6.21875,\r\n            6.229166666666667,\r\n            6.239583333333333,\r\n            6.25,\r\n            6.260416666666667,\r\n            6.270833333333333,\r\n            6.28125,\r\n            6.291666666666667,\r\n            6.302083333333333,\r\n            6.3125,\r\n            6.322916666666667,\r\n            6.333333333333333,\r\n            6.34375,\r\n            6.354166666666667,\r\n            6.364583333333333,\r\n            6.375,\r\n            6.385416666666667,\r\n            6.395833333333333,\r\n            6.40625,\r\n            6.416666666666667,\r\n            6.427083333333333,\r\n            6.4375,\r\n            6.447916666666667,\r\n            6.458333333333333,\r\n            6.46875,\r\n            6.479166666666667,\r\n            6.489583333333333,\r\n            6.5\r\n        ],\r\n        "values": [\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            126.67163111911553,\r\n            122.35395578646565,\r\n            121.6247754102202,\r\n            120.62560387451754,\r\n            119.18343006254977,\r\n            117.89744500762046,\r\n            118.1342730780976,\r\n            118.08445500139055,\r\n            118.54945325180833,\r\n            118.76942400995969,\r\n            118.90735237846692,\r\n            119.6773027172429,\r\n            122.65086608781644,\r\n            126.37976816688663,\r\n            127.59259160541846,\r\n            128.12856348164246,\r\n            128.58636375902282,\r\n            129.29798079312178,\r\n            129.95107736761514,\r\n            130.18280061752685,\r\n            130.16304963263937,\r\n            130.09522361603587,\r\n            130.14859705257794,\r\n            130.07822015941952,\r\n            130.39776736040767,\r\n            130.5388298850403,\r\n            130.51204653344357,\r\n            129.89179372603087,\r\n            124.43262686290952,\r\n            128.27321550483413,\r\n            123.78854310212148,\r\n            124.9137712962845,\r\n            130.78260655568087,\r\n            133.47301552353946,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            0.0,\r\n            130.1593772663122,\r\n            122.96182884400378,\r\n            117.39945253743676,\r\n            111.79471795026136,\r\n            108.60080993943298,\r\n            104.12101521890497,\r\n            103.3565693227702,\r\n            146.40828864918254,\r\n            142.24523933078657,\r\n            162.19363151948082,\r\n            166.57327904039352,\r\n            166.39209087365126,\r\n            173.23861966391115\r\n        ]\r\n    },\r\n    "ref_intensity_data": {\r\n        "times": [\r\n            1.0,\r\n            1.025,\r\n            1.05,\r\n            1.0750000000000002,\r\n            1.1\r\n        ],\r\n        "values": [\r\n            76.57738841707076,\r\n            77.83117659410385,\r\n            78.57103844715,\r\n            78.88173075832528,\r\n            78.96840575624839\r\n        ]\r\n    },\r\n    "test_intensity_data": {\r\n        "times": [\r\n            5.5,\r\n            5.508620689655173,\r\n            5.517241379310345,\r\n            5.525862068965517,\r\n            5.5344827586206895,\r\n            5.543103448275862,\r\n            5.551724137931035,\r\n            5.560344827586207,\r\n            5.568965517241379,\r\n            5.577586206896552,\r\n            5.586206896551724,\r\n            5.594827586206897,\r\n            5.603448275862069,\r\n            5.612068965517241,\r\n            5.620689655172414,\r\n            5.629310344827586,\r\n            5.637931034482759,\r\n            5.646551724137931,\r\n            5.655172413793103,\r\n            5.663793103448276,\r\n            5.672413793103448,\r\n            5.681034482758621,\r\n            5.689655172413793,\r\n            5.698275862068965,\r\n            5.706896551724138,\r\n            5.7155172413793105,\r\n            5.724137931034483,\r\n            5.732758620689655,\r\n            5.741379310344827,\r\n            5.75,\r\n            5.758620689655173,\r\n            5.767241379310345,\r\n            5.775862068965517,\r\n            5.7844827586206895,\r\n            5.793103448275862,\r\n            5.801724137931035,\r\n            5.810344827586207,\r\n            5.818965517241379,\r\n            5.827586206896552,\r\n            5.836206896551724,\r\n            5.844827586206897,\r\n            5.853448275862069,\r\n            5.862068965517241,\r\n            5.870689655172414,\r\n            5.879310344827586,\r\n            5.887931034482759,\r\n            5.896551724137931,\r\n            5.905172413793103,\r\n            5.913793103448276,\r\n            5.922413793103448,\r\n            5.931034482758621,\r\n            5.939655172413793,\r\n            5.948275862068965,\r\n            5.956896551724138,\r\n            5.9655172413793105,\r\n            5.974137931034483,\r\n            5.982758620689655,\r\n            5.991379310344827,\r\n            6.0,\r\n            6.008620689655173,\r\n            6.017241379310345,\r\n            6.025862068965517,\r\n            6.0344827586206895,\r\n            6.043103448275862,\r\n            6.051724137931035,\r\n            6.060344827586206,\r\n            6.068965517241379,\r\n            6.077586206896552,\r\n            6.086206896551724,\r\n            6.094827586206897,\r\n            6.103448275862069,\r\n            6.112068965517241,\r\n            6.120689655172414,\r\n            6.129310344827586,\r\n            6.137931034482759,\r\n            6.146551724137931,\r\n            6.155172413793103,\r\n            6.163793103448276,\r\n            6.172413793103448,\r\n            6.181034482758621,\r\n            6.189655172413794,\r\n            6.198275862068965,\r\n            6.206896551724138,\r\n            6.2155172413793105,\r\n            6.224137931034483,\r\n            6.232758620689655,\r\n            6.241379310344827,\r\n            6.25,\r\n            6.258620689655173,\r\n            6.267241379310345,\r\n            6.275862068965517,\r\n            6.2844827586206895,\r\n            6.293103448275862,\r\n            6.301724137931035,\r\n            6.310344827586206,\r\n            6.318965517241379,\r\n            6.327586206896552,\r\n            6.336206896551724,\r\n            6.344827586206897,\r\n            6.353448275862069,\r\n            6.362068965517241,\r\n            6.370689655172414,\r\n            6.379310344827586,\r\n            6.387931034482759,\r\n            6.396551724137931,\r\n            6.405172413793103,\r\n            6.413793103448276,\r\n            6.422413793103448,\r\n            6.431034482758621,\r\n            6.439655172413793,\r\n            6.448275862068965,\r\n            6.456896551724138,\r\n            6.4655172413793105,\r\n            6.474137931034483,\r\n            6.482758620689655,\r\n            6.491379310344827,\r\n            6.5\r\n        ],\r\n        "values": [\r\n            42.60877441549158,\r\n            43.31571277749596,\r\n            44.01516459217306,\r\n            45.185476008539986,\r\n            47.19525328772691,\r\n            49.458600217055974,\r\n            49.156278572489576,\r\n            46.55797019344136,\r\n            44.373948052507046,\r\n            44.913062359026725,\r\n            46.320169804713,\r\n            46.09902010563609,\r\n            43.73097317021006,\r\n            41.78929233016011,\r\n            42.90225878292671,\r\n            44.38921618223422,\r\n            45.11466927062245,\r\n            47.29313052100131,\r\n            49.69699839860751,\r\n            50.69758241897369,\r\n            51.0909031633685,\r\n            51.40102936040823,\r\n            51.004819612578444,\r\n            50.253156591129994,\r\n            49.768519293012076,\r\n            49.310153193818316,\r\n            48.518470094989766,\r\n            47.576873163533634,\r\n            47.099991821550226,\r\n            46.60455747431265,\r\n            45.074707704276875,\r\n            43.5830711941218,\r\n            43.18436067550343,\r\n            42.45919724613977,\r\n            41.930140848381455,\r\n            42.39307678347562,\r\n            42.65652424097602,\r\n            42.6674928405336,\r\n            42.459197731182016,\r\n            43.702442002219364,\r\n            46.81907254738857,\r\n            48.31968885578906,\r\n            47.69233645587352,\r\n            45.470796804651776,\r\n            44.50268639931618,\r\n            45.03703694099051,\r\n            45.76134043587547,\r\n            47.914411019008824,\r\n            48.63219777441745,\r\n            47.39145400171676,\r\n            48.31736986380997,\r\n            53.97137041739428,\r\n            63.641274773072396,\r\n            70.7540167748556,\r\n            74.51242170311426,\r\n            76.16755336698236,\r\n            77.42991945176794,\r\n            78.66068234478709,\r\n            79.63100628667506,\r\n            80.41436174029464,\r\n            81.25795998435987,\r\n            82.38240722352214,\r\n            83.58424614716718,\r\n            84.6084327986519,\r\n            85.42395729652823,\r\n            85.99726082747452,\r\n            86.2697742412225,\r\n            86.16868673511037,\r\n            85.61033441348648,\r\n            84.30573744671767,\r\n            82.27925568478753,\r\n            80.83462121824526,\r\n            80.97776011390671,\r\n            81.5075435896571,\r\n            81.67068763472818,\r\n            81.49118997402095,\r\n            81.22358012455891,\r\n            81.02458011310841,\r\n            80.89537179855681,\r\n            80.75688320938167,\r\n            80.55005953656524,\r\n            80.33620256486579,\r\n            80.13608097807882,\r\n            79.9149214189148,\r\n            79.77573395908983,\r\n            79.74603305415029,\r\n            79.74677182265705,\r\n            79.5391952180121,\r\n            78.8109082457761,\r\n            77.32878972559276,\r\n            75.07410292515424,\r\n            73.22334694717811,\r\n            72.14383800530382,\r\n            71.12761743146413,\r\n            70.1441435484387,\r\n            68.79825192621541,\r\n            66.21803878150189,\r\n            62.07960586042656,\r\n            58.90727904633568,\r\n            59.262460914740736,\r\n            60.863490945808124,\r\n            67.94292818221992,\r\n            73.87247638061484,\r\n            76.660474694892,\r\n            77.28792145529427,\r\n            77.00596103955333,\r\n            76.96599054501107,\r\n            77.18657841684674,\r\n            76.63202882462397,\r\n            74.98999908214418,\r\n            73.53157603802897,\r\n            72.60433906359864,\r\n            72.35524302407521,\r\n            70.8517701490396,\r\n            67.5762315887435,\r\n            69.61427705408038,\r\n            71.19266778025694\r\n        ]\r\n    },\r\n    "ref_formants_avg": {\r\n        "F1": [\r\n            624.0388192921818,\r\n            0,\r\n            0,\r\n            0,\r\n            0,\r\n            0\r\n        ],\r\n        "F2": [\r\n            2402.9600894131695,\r\n            0,\r\n            0,\r\n            0,\r\n            0,\r\n            0\r\n        ]\r\n    },\r\n    "test_formants_avg": {\r\n        "F1": [\r\n            517.6092089065803,\r\n            0,\r\n            0,\r\n            0,\r\n            0,\r\n            0\r\n        ],\r\n        "F2": [\r\n            1673.153836041118,\r\n            0,\r\n            0,\r\n            0,\r\n            0,\r\n            0\r\n        ]\r\n    }\r\n}
\.


--
-- Data for Name: streak; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.streak (strict_id, song_jacket, streak_date, user_internal_id) FROM stdin;
2	tssetsetse2	2024-05-02	74cb4a7c-1751-4ede-829a-de5046ef4688
3	tssetsetse3	2024-05-03	74cb4a7c-1751-4ede-829a-de5046ef4688
4	44	2024-05-04	74cb4a7c-1751-4ede-829a-de5046ef4688
5	tssetsetse5	2024-05-05	74cb4a7c-1751-4ede-829a-de5046ef4688
6	tssetsetse6	2024-05-06	74cb4a7c-1751-4ede-829a-de5046ef4688
7	tssetsetse7	2024-05-07	74cb4a7c-1751-4ede-829a-de5046ef4688
8	tssetsetse8	2024-05-08	74cb4a7c-1751-4ede-829a-de5046ef4688
9	tssetsetse9	2024-05-09	74cb4a7c-1751-4ede-829a-de5046ef4688
10	tssetsetse10	2024-05-01	74cb4a7c-1751-4ede-829a-de5046ef4688
11	tssetsetse11	2024-04-28	74cb4a7c-1751-4ede-829a-de5046ef4688
12	tssetsetse12	2024-04-30	74cb4a7c-1751-4ede-829a-de5046ef4688
13	tssetsetse13	2024-04-29	74cb4a7c-1751-4ede-829a-de5046ef4688
1	tssetsetse1	2024-05-02	74cb4a7c-1751-4ede-829a-de5046ef4688
14	tssetsetse2	2024-05-02	0032c8bd-47c0-488a-b4aa-066c8e3e249e
15	tssetsetse3	2024-05-03	0032c8bd-47c0-488a-b4aa-066c8e3e249e
16	44	2024-05-04	74cb4a7c-1751-4ede-829a-de5046ef4688
17	tssetsetse5	2024-05-05	0032c8bd-47c0-488a-b4aa-066c8e3e249e
18	tssetsetse6	2024-05-06	0032c8bd-47c0-488a-b4aa-066c8e3e249e
19	tssetsetse7	2024-05-07	0032c8bd-47c0-488a-b4aa-066c8e3e249e
20	tssetsetse8	2024-05-08	0032c8bd-47c0-488a-b4aa-066c8e3e249e
21	tssetsetse9	2024-05-09	0032c8bd-47c0-488a-b4aa-066c8e3e249e
22	tssetsetse10	2024-05-01	0032c8bd-47c0-488a-b4aa-066c8e3e249e
23	tssetsetse11	2024-04-28	0032c8bd-47c0-488a-b4aa-066c8e3e249e
24	tssetsetse12	2024-04-30	0032c8bd-47c0-488a-b4aa-066c8e3e249e
25	tssetsetse13	2024-04-29	74cb4a7c-1751-4ede-829a-de5046ef4688
26	tssetsetse1	2024-05-02	0032c8bd-47c0-488a-b4aa-066c8e3e249e
\.


--
-- Data for Name: studylist; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.studylist (studylist_id, song_id, user_internal_id) FROM stdin;
5	08k0JhCj8oJLB7Xuclr57s	74cb4a7c-1751-4ede-829a-de5046ef4688
6	7aohwSiTDju51QmC54AUba	74cb4a7c-1751-4ede-829a-de5046ef4688
7	3SBzDgdTwHOMSik82ZI6L2	74cb4a7c-1751-4ede-829a-de5046ef4688
8	3qmncUJvABe0QDRwMZhbPt	74cb4a7c-1751-4ede-829a-de5046ef4688
9	3qmncUJvABe0QDRwMZhbPt	0032c8bd-47c0-488a-b4aa-066c8e3e249e
10	7aohwSiTDju51QmC54AUba	0032c8bd-47c0-488a-b4aa-066c8e3e249e
11	1955ZZJe1TzmSR0TomnNjI	0032c8bd-47c0-488a-b4aa-066c8e3e249e
12	08k0JhCj8oJLB7Xuclr57s	0032c8bd-47c0-488a-b4aa-066c8e3e249e
13	1G391cbiT3v3Cywg8T7DM1	0032c8bd-47c0-488a-b4aa-066c8e3e249e
14	1EzrEOXmMH3G43AXT1y7pA	8bb5021f-2f45-4e06-a97a-2c778c415f1c
15	6D33wCKzWtNEgOovgeVJ7r	8bb5021f-2f45-4e06-a97a-2c778c415f1c
16	6NPVVeHetUBa6NhYSaMo1w	b0355cbc-796b-4138-b598-8b09c13de125
17	2FEWcWHnDmGD6WSqpW4VYu	b0355cbc-796b-4138-b598-8b09c13de125
18	2e9Nba9tuujC3eehtJ5KQ2	b0355cbc-796b-4138-b598-8b09c13de125
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.users (user_id, user_internal_id, user_sns_id, user_name, user_image, user_exp, user_created_at, user_deleted_at, user_is_activate, user_role) FROM stdin;
1	49cd6b6c-4c9d-4aa8-b92c-84577000f3a2	12312313	ksh	eocnddlalwl.jpg	0	2024-04-30	\N	t	USER
2	74cb4a7c-1751-4ede-829a-de5046ef4688	123123143	ksh	eocnddlalwl.jpg	0	2024-04-30	\N	t	USER
3	18015293-6ac7-4c2a-921e-740fdc547eb8	1232123143	ksh	eocnddlalwl.jpg	0	2024-05-01	\N	t	USER
4	76b1b27f-2fea-4680-a516-dd71801af87f	1233143	sj	eocnddlalwl.jpg	0	2024-05-01	\N	t	USER
6	34427ef0-602e-420c-80b1-4f027708cea1	testtest	yyk	123	0	2024-05-03	\N	t	USER
8	8ea38848-24bd-4f08-9e81-7a38718b5ced	테스트	123	123	0	2024-05-03	\N	t	USER
9	27959a12-c282-48fc-9427-7bdb2e7b4fd3	testid	testname	testimage	0	2024-05-03	\N	t	USER
\.


--
-- Data for Name: wrong; Type: TABLE DATA; Schema: public; Owner: smudy
--

COPY public.wrong (wrong_id, lyric_sentence_en, lyric_sentence_ko, user_internal_id) FROM stdin;
\.


--
-- Name: learn_report_express_learn_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.learn_report_express_learn_report_id_seq', 2, true);


--
-- Name: learn_report_fill_learn_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.learn_report_fill_learn_report_id_seq', 1, true);


--
-- Name: learn_report_learn_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.learn_report_learn_report_id_seq', 23, true);


--
-- Name: learn_report_pick_learn_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.learn_report_pick_learn_report_id_seq', 1, false);


--
-- Name: learn_report_pronounce_learn_report_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.learn_report_pronounce_learn_report_id_seq', 1, false);


--
-- Name: streak_strict_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.streak_strict_id_seq', 26, true);


--
-- Name: studylist_studylist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.studylist_studylist_id_seq', 18, true);


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: smudy
--

SELECT pg_catalog.setval('public.users_user_id_seq', 16, true);


--
-- Name: learn_report_express learn_report_express_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_express
    ADD CONSTRAINT learn_report_express_pkey PRIMARY KEY (learn_report_id);


--
-- Name: learn_report_fill learn_report_fill_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_fill
    ADD CONSTRAINT learn_report_fill_pkey PRIMARY KEY (learn_report_id);


--
-- Name: learn_report_pick learn_report_pick_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pick
    ADD CONSTRAINT learn_report_pick_pkey PRIMARY KEY (learn_report_id);


--
-- Name: learn_report learn_report_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report
    ADD CONSTRAINT learn_report_pkey PRIMARY KEY (learn_report_id);


--
-- Name: learn_report_pronounce learn_report_pronounce_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pronounce
    ADD CONSTRAINT learn_report_pronounce_pkey PRIMARY KEY (learn_report_id);


--
-- Name: streak streak_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.streak
    ADD CONSTRAINT streak_pkey PRIMARY KEY (strict_id);


--
-- Name: studylist studylist_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.studylist
    ADD CONSTRAINT studylist_pkey PRIMARY KEY (studylist_id);


--
-- Name: users uk_user_internal_id; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_user_internal_id UNIQUE (user_internal_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- Name: wrong wrong_pkey; Type: CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.wrong
    ADD CONSTRAINT wrong_pkey PRIMARY KEY (wrong_id);


--
-- Name: learn_report fk_learn_report_user; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report
    ADD CONSTRAINT fk_learn_report_user FOREIGN KEY (user_internal_id) REFERENCES public.users(user_internal_id);


--
-- Name: streak fk_streak_user; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.streak
    ADD CONSTRAINT fk_streak_user FOREIGN KEY (user_internal_id) REFERENCES public.users(user_internal_id);


--
-- Name: studylist fk_studylist_user; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.studylist
    ADD CONSTRAINT fk_studylist_user FOREIGN KEY (user_internal_id) REFERENCES public.users(user_internal_id);


--
-- Name: wrong fk_wrong_user; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.wrong
    ADD CONSTRAINT fk_wrong_user FOREIGN KEY (user_internal_id) REFERENCES public.users(user_internal_id);


--
-- Name: learn_report_express learn_report_express_learn_report_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_express
    ADD CONSTRAINT learn_report_express_learn_report_id_fkey FOREIGN KEY (learn_report_id) REFERENCES public.learn_report(learn_report_id);


--
-- Name: learn_report_fill learn_report_fill_learn_report_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_fill
    ADD CONSTRAINT learn_report_fill_learn_report_id_fkey FOREIGN KEY (learn_report_id) REFERENCES public.learn_report(learn_report_id);


--
-- Name: learn_report_pick learn_report_pick_learn_report_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pick
    ADD CONSTRAINT learn_report_pick_learn_report_id_fkey FOREIGN KEY (learn_report_id) REFERENCES public.learn_report(learn_report_id);


--
-- Name: learn_report_pronounce learn_report_pronounce_learn_report_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: smudy
--

ALTER TABLE ONLY public.learn_report_pronounce
    ADD CONSTRAINT learn_report_pronounce_learn_report_id_fkey FOREIGN KEY (learn_report_id) REFERENCES public.learn_report(learn_report_id);


--
-- PostgreSQL database dump complete
--

